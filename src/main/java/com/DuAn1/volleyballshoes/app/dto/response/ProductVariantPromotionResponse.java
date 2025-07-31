package com.DuAn1.volleyballshoes.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantPromotionResponse {
    private Long id;
    private ProductVariantResponse productVariant;
    private PromotionResponse promotion;
    private BigDecimal originalPrice;
    private BigDecimal discountAmount;
    private Double discountPercent;
    private BigDecimal finalPrice;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductVariantResponse {
        private Long id;
        private String sku;
        private String barcode;
        private String productName;
        private String colorName;
        private String sizeName;
        private String soleTypeName;
        private BigDecimal price;
        private String imageUrl;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionResponse {
        private Long id;
        private String code;
        private String name;
        private String description;
        private String type; // PERCENT or AMOUNT
    }
}
