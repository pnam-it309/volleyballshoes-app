package com.DuAn1.volleyballshoes.app.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private int productId;
    private int brandId;
    private int categoryId;
    private String productName;
    private String productDesc;
    private LocalDateTime productCreateAt;
    private LocalDateTime productUpdatedAt;
} 