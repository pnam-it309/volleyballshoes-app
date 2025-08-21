package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.*;
import com.DuAn1.volleyballshoes.app.dao.impl.*;
import com.DuAn1.volleyballshoes.app.dto.request.*;
import com.DuAn1.volleyballshoes.app.dto.response.*;
import com.DuAn1.volleyballshoes.app.entity.*;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.entity.Staff;
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
                .orderPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "Tiền mặt")
                .orderStatus("Chưa thanh toán")
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
        System.out.println("OrderController.getAllOrders() called");
        if (orderDAO == null) {
            String error = "Lỗi: orderDAO is null";
            System.err.println(error);
            NotificationUtil.showError(parentFrame, "Lỗi kết nối cơ sở dữ liệu");
            return new ArrayList<>();
        }
        try {
            System.out.println("Fetching all orders from DAO...");
            List<Order> orders = orderDAO.findAll();
            System.out.println("Found " + (orders != null ? orders.size() : 0) + " orders in DAO");
            
            if (orders != null && !orders.isEmpty()) {
                System.out.println("Sample order from DAO - ID: " + orders.get(0).getOrderId() + 
                                 ", Status: " + orders.get(0).getOrderStatus());
            }
            
            List<OrderResponse> responses = orders.stream()
                    .map(order -> {
                        System.out.println("Converting order ID: " + order.getOrderId());
                        return getOrderResponse(order);
                    })
                    .collect(Collectors.toList());
                    
            System.out.println("Converted " + responses.size() + " orders to OrderResponse");
            return responses;
            
        } catch (Exception e) {
            System.err.println("Error in getAllOrders: " + e.getMessage());
            e.printStackTrace();
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

            if ("Hủy hóa đơn".equals(order.getOrderStatus())) {
                NotificationUtil.showWarning(parentFrame, "Đơn hàng đã bị hủy trước đó");
                return false;
            }

            if ("Đã thanh toán".equals(order.getOrderStatus())) {
                NotificationUtil.showWarning(parentFrame, "Không thể hủy đơn hàng đã hoàn thành");
                return false;
            }

            // Update order status
            order.setOrderStatus("Hủy hóa đơn");
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

        // Get customer name if customer exists
        String customerName = "Khách lẻ";
        if (order.getCustomerId() != null && customerDAO != null) {
            try {
                Customer customer = customerDAO.findById(order.getCustomerId());
                if (customer != null) {
                    customerName = customer.getCustomerFullName();
                }
            } catch (Exception e) {
                System.err.println("Error loading customer name: " + e.getMessage());
            }
        }

        // Get staff name
        String staffName = "";
        if (order.getStaffId() > 0 && staffDAO != null) {
            try {
                Staff staff = staffDAO.findById(order.getStaffId());
                if (staff != null) {
                    staffName = staff.getStaffUsername();
                }
            } catch (Exception e) {
                System.err.println("Error loading staff name: " + e.getMessage());
            }
        }

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderCode(order.getOrderCode())
                .customerId(order.getCustomerId())
                .customerName(customerName)
                .staffId(order.getStaffId())
                .staffName(staffName)
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
    
    /**
     * Get order with all its details by order code
     * @param orderCode The order code to search for
     * @return OrderWithDetailsResponse containing order and its details, or null if not found
     */
    public OrderWithDetailsResponse getOrderWithDetails(String orderCode) {
        try {
            if (orderDAO == null) {
                throw new IllegalStateException("OrderDAO is not initialized");
            }
            return ((OrderDAOImpl) orderDAO).findOrderWithDetails(orderCode);
        } catch (Exception e) {
            if (parentFrame != null) {
                NotificationUtil.showError(parentFrame, "Lỗi khi lấy thông tin đơn hàng: " + e.getMessage());
            }
            return null;
        }
    }
}
