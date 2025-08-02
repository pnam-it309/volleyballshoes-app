package com.DuAn1.volleyballshoes.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantPromotionRequest {

    private Long id;

    private Long productVariantId;

    private Long promotionId;

    private BigDecimal discountAmount;

    private Double discountPercent;

    private BigDecimal finalPrice;

    private String note;
}
