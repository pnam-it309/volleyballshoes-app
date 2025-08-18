package com.DuAn1.volleyballshoes.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    private Long variantId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountPercent;
    private BigDecimal total;
}
