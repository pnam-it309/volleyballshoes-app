package com.DuAn1.volleyballshoes.app.dto.response;

import com.DuAn1.volleyballshoes.app.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private String orderCode;
    private Integer customerId;
    private String customerName;
    private Integer staffId;
    private String staffName;
    private BigDecimal finalAmount;
    private String paymentMethod;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
    
    public static OrderResponse fromEntity(Order order) {
        if (order == null) return null;
        
        return OrderResponse.builder()
            .orderId(order.getOrderId())
            .orderCode(order.getOrderCode())
            .customerId(order.getCustomerId())
            .staffId(order.getStaffId())
            .finalAmount(order.getOrderFinalAmount())
            .paymentMethod(order.getOrderPaymentMethod())
            .status(order.getOrderStatus())
            .createdAt(order.getOrderCreatedAt())
            .build();
    }
}
