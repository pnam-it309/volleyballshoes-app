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

}
