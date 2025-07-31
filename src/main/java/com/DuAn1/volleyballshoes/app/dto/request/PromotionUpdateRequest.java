package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PromotionUpdateRequest {
    private String name;
    private String description;
    private String discountType;
    
    @Positive(message = "Giá trị giảm giá phải lớn hơn 0")
    private BigDecimal discountValue;
    
    private LocalDateTime startDate;
    
    @Future(message = "Ngày kết thúc phải trong tương lai")
    private LocalDateTime endDate;
    
    private Boolean active;
    private List<Long> productVariantIds;
}
