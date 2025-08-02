package com.DuAn1.volleyballshoes.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatisticResponse {
    private Long productId;
    private String productName;
    private String productCode;
    private String categoryName;
    private long quantitySold;
    private BigDecimal revenue;
    private BigDecimal profit;
    private String imageUrl;
}
