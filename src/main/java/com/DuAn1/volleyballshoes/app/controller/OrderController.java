package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.dto.request.*;
import com.DuAn1.volleyballshoes.app.dto.response.*;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.utils.NotificationUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class OrderController {

    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;
    private CustomerDAO customerDAO;
    private StaffDAO staffDAO;
    private ProductVariantDAO productVariantDAO;
    private JFrame parentFrame;

    public OrderController() {
        this(null);
    }

    public OrderController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        try {
            this.orderDAO = new OrderDAOImpl();
            this.orderDetailDAO = new OrderDetailDAOImpl();
            this.customerDAO = new CustomerDAOImpl();
            this.staffDAO = new StaffDAOImpl();
            this.productVariantDAO = new ProductVariantDAOImpl();
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Không thể khởi tạo OrderController: " + e.getMessage());
        }
    }

    public OrderResponse createOrder(OrderCreateRequest request) {
        if (orderDAO == null || orderDetailDAO == null || productVariantDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }

        // Kiểm tra thông tin cơ bản
        Integer staffId = request.getStaffId();
        if (staffId == null) {
            NotificationUtil.showError(parentFrame, "Thiếu thông tin nhân viên");
            return null;
        }

        // Tạo order với finalAmount = 0 nếu không có items
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .staffId(staffId)
                .orderFinalAmount(BigDecimal.ZERO)
                .orderPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CASH")
                .orderStatus("PENDING")
                .orderCreatedAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderDAO.save(order);

        // Nếu có items, thêm chi tiết hóa đơn và cập nhật total
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (var item : request.getItems()) {
                ProductVariant variant = productVariantDAO.findById(item.getVariantId());
                if (variant == null) {
                    NotificationUtil.showError(parentFrame, "Không tìm thấy biến thể sản phẩm với ID: " + item.getVariantId());
                    return null;
                }
                if (item.getQuantity() <= 0) {
                    NotificationUtil.showError(parentFrame, "Số lượng sản phẩm phải lớn hơn 0");
                    return null;
                }

                BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                totalAmount = totalAmount.add(itemTotal);

                OrderDetail orderDetail = OrderDetail.builder()
                        .orderId(savedOrder.getOrderId())
                        .variantId(item.getVariantId())
                        .detailQuantity(item.getQuantity())
                        .detailUnitPrice(item.getUnitPrice())
                        .build();
                orderDetailDAO.save(orderDetail);
            }
            savedOrder.setOrderFinalAmount(totalAmount);
            orderDAO.update(savedOrder);
        }

        return getOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Integer orderId) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }

        Optional<Order> orderOpt = orderDAO.findById(orderId);
        if (orderOpt.isEmpty()) {
            NotificationUtil.showError(parentFrame, "Không tìm thấy đơn hàng với ID: " + orderId);
            return null;
        }

        return getOrderResponse(orderOpt.get());
    }

    public List<OrderResponse> getAllOrders() {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.findAll().stream()
                    .map(order -> getOrderResponse(order))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tải danh sách đơn hàng: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrderResponse> getOrdersByCustomerId(Integer customerId) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.findByCustomerId(customerId).stream()
                    .map(order -> getOrderResponse(order))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tải đơn hàng của khách hàng: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean cancelOrder(Integer orderId) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return false;
        }

        try {
            Optional<Order> orderOpt = orderDAO.findById(orderId);
            if (orderOpt.isEmpty()) {
                NotificationUtil.showError(parentFrame, "Không tìm thấy đơn hàng với ID: " + orderId);
                return false;
            }

            Order order = orderOpt.get();

            if ("CANCELLED".equals(order.getOrderStatus())) {
                NotificationUtil.showWarning(parentFrame, "Đơn hàng đã bị hủy trước đó");
                return false;
            }

            if ("COMPLETED".equals(order.getOrderStatus())) {
                NotificationUtil.showWarning(parentFrame, "Không thể hủy đơn hàng đã hoàn thành");
                return false;
            }

            // Update order status
            order.setOrderStatus("CANCELLED");
            orderDAO.save(order);
            NotificationUtil.showSuccess(parentFrame, "Đã hủy đơn hàng thành công");
            return true;

        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi hủy đơn hàng: " + e.getMessage());
            return false;
        }
    }

    public List<OrderResponse> searchOrders(String keyword) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.search(keyword).stream()
                    .map(order -> getOrderResponse(order))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tìm kiếm đơn hàng: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public int getTotalPages(int pageSize) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return 0;
        }
        try {
            // Lấy tất cả đơn hàng và tính toán số trang dựa trên kích thước trang
            List<Order> allOrders = orderDAO.findAll();
            if (allOrders == null || allOrders.isEmpty()) {
                return 0;
            }
            return (int) Math.ceil((double) allOrders.size() / pageSize);
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tính số trang: " + e.getMessage());
            return 0;
        }
    }

    private OrderResponse getOrderResponse(Order order) {
        List<OrderDetail> details = new ArrayList<>();
        if (orderDetailDAO != null) {
            try {
                details = orderDetailDAO.findByOrderId(order.getOrderId());
            } catch (Exception e) {
                NotificationUtil.showError(parentFrame, "Lỗi khi tải chi tiết đơn hàng: " + e.getMessage());
            }
        }

        List<OrderItemResponse> itemResponses = details.stream()
                .map(detail -> OrderItemResponse.builder()
                .orderDetailId(detail.getOrderDetailId())
                .orderId(detail.getOrderId())
                .variantId(detail.getVariantId())
                .quantity(detail.getDetailQuantity())
                .unitPrice(detail.getDetailUnitPrice())
                .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .customerId(order.getCustomerId())
                .staffId(order.getStaffId())
                .finalAmount(order.getOrderFinalAmount())
                .paymentMethod(order.getOrderPaymentMethod())
                .status(order.getOrderStatus())
                .createdAt(order.getOrderCreatedAt())
                .items(itemResponses)
                .build();
    }

    public List<OrderResponse> findByStatus(String status) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.findByStatus(status).stream()
                    .map(order -> getOrderResponse(order))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tìm kiếm đơn hàng theo trạng thái: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrderResponse> findByCreatedDateBetween(Date from, Date to) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.findByCreatedDateBetween(from, to).stream()
                    .map(order -> getOrderResponse(order))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tìm kiếm đơn hàng theo ngày: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrderResponse> findByTotalAmountBetween(double min, double max) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            return orderDAO.findByTotalAmountBetween(min, max).stream()
                    .map(order -> getOrderResponse(order))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tìm kiếm đơn hàng theo giá: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public OrderResponse getOrderByQRCodeOrId(String codeOrId) {
        if (orderDAO == null) {
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return null;
        }
        try {
            // Thử tìm theo mã đơn hàng (orderCode)
            Optional<Order> orderOpt = orderDAO.findByCode(codeOrId);
            if (orderOpt.isPresent()) {
                return getOrderResponse(orderOpt.get());
            }
            // Nếu không có, thử parse sang số và tìm theo ID
            try {
                int id = Integer.parseInt(codeOrId);
                return getOrderById(id);
            } catch (NumberFormatException ignore) {
            }
            NotificationUtil.showError(parentFrame, "Không tìm thấy đơn hàng với mã hoặc ID: " + codeOrId);
            return null;
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi tìm đơn hàng: " + e.getMessage());
            return null;
        }
    }

    public OrderResponse updateOrder(Integer orderId, OrderResponse request) {
        try {
            Optional<Order> orderOpt = orderDAO.findById(orderId);
            if (orderOpt.isEmpty()) {
                NotificationUtil.showError(parentFrame, "Không tìm thấy đơn hàng với ID: " + orderId);
                return null;
            }

            Order order = orderOpt.get();
            order.setCustomerId(request.getCustomerId());
            order.setStaffId(request.getStaffId());
            order.setOrderFinalAmount(request.getFinalAmount());
            order.setOrderPaymentMethod(request.getPaymentMethod());
            order.setOrderStatus(request.getStatus());

            // Xóa chi tiết cũ
            orderDetailDAO.deleteByOrderId(orderId);

            // Thêm chi tiết mới
            for (OrderItemResponse item : request.getItems()) {
                OrderDetail detail = OrderDetail.builder()
                        .orderId(orderId)
                        .variantId(item.getVariantId())
                        .detailQuantity(item.getQuantity())
                        .detailUnitPrice(item.getUnitPrice())
                        .detailDiscountPercent(BigDecimal.ZERO)
                        .detailTotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build();
                orderDetailDAO.save(detail);
            }

            orderDAO.update(order);
            return getOrderResponse(order);
        } catch (Exception e) {
            NotificationUtil.showError(parentFrame, "Lỗi khi cập nhật đơn hàng: " + e.getMessage());
            return null;
        }
    }
}
