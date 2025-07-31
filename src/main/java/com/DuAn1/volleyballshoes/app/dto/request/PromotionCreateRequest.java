package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PromotionCreateRequest {
    @NotBlank(message = "Tên chương trình khuyến mãi không được để trống")
    private String name;

    private String description;
    
    @NotBlank(message = "Loại giảm giá không được để trống")
    private String discountType; // PERCENTAGE, FIXED_AMOUNT
    
    @NotNull(message = "Giá trị giảm giá không được để trống")
    @Positive(message = "Giá trị giảm giá phải lớn hơn 0")
    private BigDecimal discountValue;
    
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDateTime startDate;
    
    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải trong tương lai")
    private LocalDateTime endDate;
    
    private List<Long> productVariantIds; // Danh sách ID biến thể sản phẩm áp dụng
}
