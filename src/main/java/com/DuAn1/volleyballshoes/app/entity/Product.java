package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
    private String productDesc;
    private LocalDateTime productCreateAt;
    private LocalDateTime productUpdatedAt;
    private List<ProductVariant> variants;
}
