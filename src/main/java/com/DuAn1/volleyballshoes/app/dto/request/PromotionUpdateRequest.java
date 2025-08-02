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

    private BigDecimal discountValue;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean active;
    private List<Long> productVariantIds;
}
