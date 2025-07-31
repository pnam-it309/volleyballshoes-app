package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    
    @NotNull(message = "ID biến thể sản phẩm không được để trống")
    private Long productVariantId;
    
    @NotNull(message = "ID khuyến mãi không được để trống")
    private Long promotionId;
    
    @NotNull(message = "Giảm giá không được để trống")
    @PositiveOrZero(message = "Giảm giá phải lớn hơn hoặc bằng 0")
    private BigDecimal discountAmount;
    
    @PositiveOrZero(message = "Phần trăm giảm giá phải lớn hơn hoặc bằng 0")
    private Double discountPercent;
    
    @NotNull(message = "Giá sau giảm không được để trống")
    @PositiveOrZero(message = "Giá sau giảm phải lớn hơn hoặc bằng 0")
    private BigDecimal finalPrice;
    
    private String note;
}
