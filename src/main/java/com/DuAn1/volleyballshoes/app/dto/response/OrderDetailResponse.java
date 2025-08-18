package com.DuAn1.volleyballshoes.app.dto.response;

import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private String productName;
    private String colorName;
    private String sizeName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discountPercent;
}
