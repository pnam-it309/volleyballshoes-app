package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.dto.response.BillResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderDetailResponse;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.dao.impl.OrderDAOImpl;
import com.DuAn1.volleyballshoes.app.dao.impl.OrderDetailDAOImpl;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class BillController {
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;
    
    private JFrame parentFrame;
    
    public BillController() {
        this(null);
    }
    
    public BillController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        try {
            this.orderDAO = new OrderDAOImpl();
            this.orderDetailDAO = new OrderDetailDAOImpl();
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Không thể khởi tạo BillController: " + e.getMessage());
        }
    }

    public List<BillResponse> getAllBills() {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.findAll().stream()
                    .map(this::mapToBillResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tải danh sách hóa đơn: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public BillResponse getBillById(Integer id) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }
        
        Optional<Order> orderOpt = orderDAO.findById(id);
        if (orderOpt.isEmpty()) {
            NotificationUtil.showError(parentFrame, "Không tìm thấy hóa đơn với ID: " + id);
            return null;
        }
        return mapToBillResponse(orderOpt.get());
    }

    public List<BillResponse> searchBills(String keyword, LocalDateTime fromDate, LocalDateTime toDate) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        
        try {
            return orderDAO.findAll().stream()
                    .filter(order -> {
                        boolean matchesKeyword = keyword == null || keyword.trim().isEmpty() ||
                                String.valueOf(order.getOrderId()).contains(keyword) ||
                                String.valueOf(order.getCustomerId()).contains(keyword);
                        boolean matchesDate = isDateInRange(order.getOrderCreatedAt(), fromDate, toDate);
                        return matchesKeyword && matchesDate;
                    })
                    .map(this::mapToBillResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean isDateInRange(LocalDateTime date, LocalDateTime fromDate, LocalDateTime toDate) {
        if (fromDate != null && date.isBefore(fromDate)) {
            return false;
        }
        if (toDate != null && date.isAfter(toDate)) {
            return false;
        }
        return true;
    }

    private BillResponse mapToBillResponse(Order order) {
        List<OrderDetail> orderDetails = orderDetailDAO.findByOrderId(order.getOrderId());
        
        List<OrderDetailResponse> detailResponses = orderDetails.stream()
                .map(detail -> OrderDetailResponse.builder()
                        .id((long)detail.getOrderDetailId())
                        .productName("Product " + detail.getVariantId()) // Placeholder - would need product service
                        .colorName("Color") // Placeholder
                        .sizeName("Size")   // Placeholder
                        .quantity(detail.getDetailQuantity())
                        .unitPrice(detail.getDetailUnitPrice())
                        .totalPrice(detail.getDetailUnitPrice()
                                .multiply(BigDecimal.valueOf(detail.getDetailQuantity())))
                        .build())
                .collect(Collectors.toList());

        BillResponse response = new BillResponse();
        response.setId((long)order.getOrderId());
        response.setCustomerName("Customer " + order.getCustomerId()); // Placeholder
        response.setStaffName("Staff " + order.getStaffId());          // Placeholder
        response.setOrderDate(order.getOrderCreatedAt());
        response.setTotalAmount(order.getOrderFinalAmount());
        response.setStatus(order.getOrderStatus());
        response.setOrderDetails(detailResponses);
        
        return response;
    }
}
