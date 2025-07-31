package com.DuAn1.volleyballshoes.app.dto.response;

import com.DuAn1.volleyballshoes.app.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Integer orderDetailId;
    private Integer orderId;
    private Integer variantId;
    private Integer quantity;
    private BigDecimal unitPrice;
    
    public static OrderItemResponse fromEntity(OrderDetail orderDetail) {
        if (orderDetail == null) return null;
        
        return OrderItemResponse.builder()
            .orderDetailId(orderDetail.getOrderDetailId())
            .orderId(orderDetail.getOrderId())
            .variantId(orderDetail.getVariantId())
            .quantity(orderDetail.getDetailQuantity())
            .unitPrice(orderDetail.getDetailUnitPrice())
            .build();
    }
}
