package com.DuAn1.volleyballshoes.app.controller;

import com.DuAn1.volleyballshoes.app.dao.CustomerDAO;
import com.DuAn1.volleyballshoes.app.dao.OrderDAO;
import com.DuAn1.volleyballshoes.app.dao.OrderDetailDAO;
import com.DuAn1.volleyballshoes.app.dao.ProductVariantDAO;
import com.DuAn1.volleyballshoes.app.dao.StaffDAO;
import com.DuAn1.volleyballshoes.app.dto.request.OrderCreateRequest;
import com.DuAn1.volleyballshoes.app.dto.response.OrderItemResponse;
import com.DuAn1.volleyballshoes.app.dto.response.OrderResponse;
import com.DuAn1.volleyballshoes.app.entity.Customer;
import com.DuAn1.volleyballshoes.app.entity.Order;
import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import com.DuAn1.volleyballshoes.app.entity.ProductVariant;
import com.DuAn1.volleyballshoes.app.entity.Staff;
import com.DuAn1.volleyballshoes.app.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final CustomerDAO customerDAO;
    private final StaffDAO staffDAO;
    private final ProductVariantDAO productVariantDAO;

    public OrderResponse createOrder(OrderCreateRequest request) {
        // Validate customer exists
        Customer customer = customerDAO.findById(request.getCustomerId())
            .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng với ID: " + request.getCustomerId()));

        // Validate staff exists
        Staff staff = staffDAO.findById(request.getStaffId())
            .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên với ID: " + request.getStaffId()));

        // Validate and process order items
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException("Đơn hàng phải có ít nhất một sản phẩm");
        }

        // Calculate total amount and validate product variants
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (var item : request.getItems()) {
            ProductVariant variant = productVariantDAO.findById(item.getVariantId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy biến thể sản phẩm với ID: " + item.getVariantId()));

            if (item.getQuantity() <= 0) {
                throw new BusinessException("Số lượng sản phẩm phải lớn hơn 0");
            }

            if (variant.getQuantityInStock() < item.getQuantity()) {
                throw new BusinessException("Số lượng sản phẩm " + variant.getVariantName() + " trong kho không đủ");
            }

            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        // Create order
        Order order = Order.builder()
            .customerId(customer.getCustomerId())
            .staffId(staff.getStaffId())
            .orderFinalAmount(totalAmount)
            .orderPaymentMethod(request.getPaymentMethod())
            .orderStatus("PENDING") // Default status
            .orderCreatedAt(LocalDateTime.now())
            .build();

        // Save order to get generated ID
        Order savedOrder = orderDAO.save(order);

        // Save order details and update product quantities
        for (var item : request.getItems()) {
            ProductVariant variant = productVariantDAO.findById(item.getVariantId()).get();
            
            // Create order detail
            OrderDetail orderDetail = OrderDetail.builder()
                .orderId(savedOrder.getOrderId())
                .variantId(item.getVariantId())
                .detailQuantity(item.getQuantity())
                .detailUnitPrice(item.getUnitPrice())
                .build();
            
            orderDetailDAO.save(orderDetail);
            
            // Update product variant quantity
            variant.setQuantityInStock(variant.getQuantityInStock() - item.getQuantity());
            productVariantDAO.save(variant);
        }

        return getOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Integer orderId) {
        Order order = orderDAO.findById(orderId)
            .orElseThrow(() -> new BusinessException("Không tìm thấy đơn hàng với ID: " + orderId));
        
        return getOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderDAO.findAll().stream()
            .map(this::getOrderResponse)
            .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByCustomerId(Integer customerId) {
        return orderDAO.findByCustomerId(customerId).stream()
            .map(this::getOrderResponse)
            .collect(Collectors.toList());
    }

    public void cancelOrder(Integer orderId) {
        Order order = orderDAO.findById(orderId)
            .orElseThrow(() -> new BusinessException("Không tìm thấy đơn hàng với ID: " + orderId));
        
        if ("CANCELLED".equals(order.getOrderStatus())) {
            throw new BusinessException("Đơn hàng đã bị hủy trước đó");
        }
        
        if ("COMPLETED".equals(order.getOrderStatus())) {
            throw new BusinessException("Không thể hủy đơn hàng đã hoàn thành");
        }
        
        // Update order status
        order.setOrderStatus("CANCELLED");
        orderDAO.save(order);
        
        // Restore product quantities
        List<OrderDetail> orderDetails = orderDetailDAO.findByOrderId(orderId);
        for (OrderDetail detail : orderDetails) {
            productVariantDAO.findById(detail.getVariantId()).ifPresent(variant -> {
                variant.setQuantityInStock(variant.getQuantityInStock() + detail.getDetailQuantity());
                productVariantDAO.save(variant);
            });
        }
    }

    public List<OrderResponse> searchOrders(String keyword) {
        return orderDAO.search(keyword).stream()
            .map(this::getOrderResponse)
            .collect(Collectors.toList());
    }

    public int getTotalPages(int pageSize) {
        long totalItems = orderDAO.count();
        return (int) Math.ceil((double) totalItems / pageSize);
    }

    private OrderResponse getOrderResponse(Order order) {
        List<OrderDetail> details = orderDetailDAO.findByOrderId(order.getOrderId());
        
        List<OrderItemResponse> itemResponses = details.stream()
            .map(detail -> {
                ProductVariant variant = productVariantDAO.findById(detail.getVariantId()).orElse(null);
                return OrderItemResponse.builder()
                    .orderDetailId(detail.getOrderDetailId())
                    .orderId(detail.getOrderId())
                    .variantId(detail.getVariantId())
                    .variantName(variant != null ? variant.getVariantName() : "N/A")
                    .quantity(detail.getDetailQuantity())
                    .unitPrice(detail.getDetailUnitPrice())
                    .build();
            })
            .collect(Collectors.toList());
        
        Customer customer = customerDAO.findById(order.getCustomerId()).orElse(null);
        Staff staff = staffDAO.findById(order.getStaffId()).orElse(null);
        
        return OrderResponse.builder()
            .orderId(order.getOrderId())
            .orderCode(order.getOrderCode())
            .customerId(order.getCustomerId())
            .customerName(customer != null ? customer.getFullName() : "N/A")
            .staffId(order.getStaffId())
            .staffName(staff != null ? staff.getFullName() : "N/A")
            .finalAmount(order.getOrderFinalAmount())
            .paymentMethod(order.getOrderPaymentMethod())
            .status(order.getOrderStatus())
            .createdAt(order.getOrderCreatedAt())
            .items(itemResponses)
            .build();
    }
}
