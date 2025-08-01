package com.DuAn1.volleyballshoes.app.dto.request;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PromotionCreateRequest {

    private String name;

    private String description;

    private String discountType;

    private BigDecimal discountValue;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<Long> productVariantIds;
}
