package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private int productId;
    private int brandId;
    private int categoryId;
    private String productName;
    private String productDescription;
    private String productCode;
    private LocalDateTime productCreateAt;
    private LocalDateTime productUpdatedAt;
}
