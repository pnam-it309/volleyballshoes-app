package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotNull(message = "Mã biến thể sản phẩm không được để trống")
    @Min(value = 1, message = "Mã biến thể sản phẩm không hợp lệ")
    private Integer variantId;
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer quantity;
    
    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 0, message = "Đơn giá không hợp lệ")
    private BigDecimal unitPrice;
}
