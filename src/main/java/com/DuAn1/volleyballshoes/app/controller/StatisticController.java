package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dto.request.StatisticRequest;
import com.DuAn1.volleyballshoes.app.dto.response.ProductStatisticResponse;
import com.DuAn1.volleyballshoes.app.dto.response.RevenueStatisticResponse;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StatisticController {

    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final ProductVariantDAO productVariantDAO;

    public List<RevenueStatisticResponse> getRevenueStatistics(StatisticRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        // Nếu không có ngày bắt đầu và kết thúc, mặc định lấy 30 ngày gần nhất
        if (startDate == null || endDate == null) {
            endDate = LocalDate.now();
            startDate = endDate.minusDays(30);
        }

        // Lấy tất cả đơn hàng trong khoảng thời gian
        List<Order> orders = orderDAO.findByOrderDateBetween(
            startDate.atStartOfDay(),
            endDate.plusDays(1).atStartOfDay()
        );

        // Nhóm theo ngày/tháng/năm tùy theo yêu cầu
        Map<LocalDate, RevenueStatisticResponse> resultMap = new TreeMap<>();
        DateTimeFormatter formatter;
        
        switch (request.getType().toUpperCase()) {
            case "YEAR":
                formatter = DateTimeFormatter.ofPattern("yyyy");
                for (int year = startDate.getYear(); year <= endDate.getYear(); year++) {
                    LocalDate yearDate = LocalDate.of(year, 1, 1);
                    resultMap.put(yearDate, RevenueStatisticResponse.builder()
                            .date(yearDate)
                            .label(String.valueOf(year))
                            .orderCount(0)
                            .totalRevenue(BigDecimal.ZERO)
                            .productSold(0)
                            .build());
                }
                break;
                
            case "MONTH":
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                LocalDate current = startDate.withDayOfMonth(1);
                while (!current.isAfter(endDate)) {
                    resultMap.put(current, RevenueStatisticResponse.builder()
                            .date(current)
                            .label(current.format(DateTimeFormatter.ofPattern("MM/yyyy")))
                            .orderCount(0)
                            .totalRevenue(BigDecimal.ZERO)
                            .productSold(0)
                            .build());
                    current = current.plusMonths(1);
                }
                break;
                
            default: // DAY
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate day = startDate;
                while (!day.isAfter(endDate)) {
                    resultMap.put(day, RevenueStatisticResponse.builder()
                            .date(day)
                            .label(day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            .orderCount(0)
                            .totalRevenue(BigDecimal.ZERO)
                            .productSold(0)
                            .build());
                    day = day.plusDays(1);
                }
                break;
        }

        // Tính toán thống kê
        for (Order order : orders) {
            LocalDate orderDate = order.getOrderDate().toLocalDate();
            LocalDate keyDate;
            
            switch (request.getType().toUpperCase()) {
                case "YEAR":
                    keyDate = LocalDate.of(orderDate.getYear(), 1, 1);
                    break;
                case "MONTH":
                    keyDate = orderDate.withDayOfMonth(1);
                    break;
                default: // DAY
                    keyDate = orderDate;
                    break;
            }
            
            if (resultMap.containsKey(keyDate)) {
                RevenueStatisticResponse stat = resultMap.get(keyDate);
                stat.setOrderCount(stat.getOrderCount() + 1);
                stat.setTotalRevenue(stat.getTotalRevenue().add(order.getTotalAmount()));
                
                // Tính tổng số sản phẩm đã bán
                long productCount = orderDetailDAO.findByOrderId(order.getId())
                        .stream()
                        .mapToLong(OrderDetail::getQuantity)
                        .sum();
                stat.setProductSold(stat.getProductSold() + productCount);
            }
        }

        return new ArrayList<>(resultMap.values());
    }

    public List<ProductStatisticResponse> getProductStatistics(StatisticRequest request) {
        LocalDateTime startDate = request.getStartDate() != null ? 
                request.getStartDate().atStartOfDay() : 
                LocalDateTime.now().minusMonths(1);
                
        LocalDateTime endDate = request.getEndDate() != null ? 
                request.getEndDate().plusDays(1).atStartOfDay() : 
                LocalDateTime.now();

        // Lấy tất cả đơn hàng chi tiết trong khoảng thời gian
        List<OrderDetail> orderDetails = orderDetailDAO.findByOrder_OrderDateBetween(startDate, endDate);

        // Nhóm theo sản phẩm
        Map<Long, ProductStatisticResponse> productStats = new HashMap<>();

        for (OrderDetail detail : orderDetails) {
            ProductVariant variant = detail.getProductVariant();
            Long productId = variant.getProduct().getId();
            
            ProductStatisticResponse stat = productStats.computeIfAbsent(productId, k -> 
                ProductStatisticResponse.builder()
                    .productId(productId)
                    .productName(variant.getProduct().getName())
                    .productCode(variant.getProduct().getCode())
                    .categoryName(variant.getProduct().getCategory() != null ? 
                                variant.getProduct().getCategory().getName() : "")
                    .quantitySold(0)
                    .revenue(BigDecimal.ZERO)
                    .profit(BigDecimal.ZERO)
                    .imageUrl(variant.getProduct().getImageUrl())
                    .build()
            );

            // Cập nhật thống kê
            stat.setQuantitySold(stat.getQuantitySold() + detail.getQuantity());
            
            BigDecimal itemRevenue = detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
            stat.setRevenue(stat.getRevenue().add(itemRevenue));
            
            // Giả sử có trường cost trong ProductVariant để tính lợi nhuận
            if (variant.getCost() != null) {
                BigDecimal itemCost = variant.getCost().multiply(BigDecimal.valueOf(detail.getQuantity()));
                BigDecimal itemProfit = itemRevenue.subtract(itemCost);
                stat.setProfit(stat.getProfit().add(itemProfit));
            }
        }

        // Sắp xếp kết quả theo số lượng bán giảm dần
        return productStats.values().stream()
                .sorted((p1, p2) -> Long.compare(p2.getQuantitySold(), p1.getQuantitySold()))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getDashboardStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime startOfYear = now.withDayOfYear(1).toLocalDate().atStartOfDay();

        // Thống kê đơn hàng
        long totalOrders = orderDAO.count();
        long todayOrders = orderDAO.countByOrderDateBetween(startOfDay, now);
        long monthlyOrders = orderDAO.countByOrderDateBetween(startOfMonth, now);
        long yearlyOrders = orderDAO.countByOrderDateBetween(startOfYear, now);

        // Thống kê doanh thu
        BigDecimal dailyRevenue = orderDAO.getTotalRevenueByDateRange(startOfDay, now);
        BigDecimal monthlyRevenue = orderDAO.getTotalRevenueByDateRange(startOfMonth, now);
        BigDecimal yearlyRevenue = orderDAO.getTotalRevenueByDateRange(startOfYear, now);
        BigDecimal totalRevenue = orderDAO.getTotalRevenue();

        // Thống kê sản phẩm
        long totalProducts = productVariantDAO.count();
        long lowStockProducts = productVariantDAO.countByQuantityLessThan(10); // Sản phẩm tồn kho thấp

        Map<String, Object> result = new HashMap<>();
        
        // Orders
        Map<String, Object> orders = new HashMap<>();
        orders.put("total", totalOrders);
        orders.put("today", todayOrders);
        orders.put("monthly", monthlyOrders);
        orders.put("yearly", yearlyOrders);
        
        // Revenue
        Map<String, Object> revenue = new HashMap<>();
        revenue.put("daily", dailyRevenue != null ? dailyRevenue : BigDecimal.ZERO);
        revenue.put("monthly", monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);
        revenue.put("yearly", yearlyRevenue != null ? yearlyRevenue : BigDecimal.ZERO);
        revenue.put("total", totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        
        // Products
        Map<String, Object> products = new HashMap<>();
        products.put("total", totalProducts);
        products.put("lowStock", lowStockProducts);
        
        result.put("orders", orders);
        result.put("revenue", revenue);
        result.put("products", products);
        
        return result;
    }
}
