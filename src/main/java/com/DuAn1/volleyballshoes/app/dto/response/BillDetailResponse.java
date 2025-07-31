package com.DuAn1.volleyballshoes.app.dto.response;

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
public class BillDetailResponse {
    private Long id;
    private String code;
    private LocalDateTime orderDate;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String staffName;
    private String paymentMethod;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private String note;
    private String shippingAddress;
    private String billingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private List<OrderItemResponse> items;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long id;
        private String productName;
        private String productCode;
        private String sku;
        private String color;
        private String size;
        private String soleType;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
        private BigDecimal discount;
        private BigDecimal total;
        private String imageUrl;
    }
}
