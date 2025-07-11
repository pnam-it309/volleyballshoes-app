package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Integer productId;
    private String name;
    private String description;
    private Double basePrice;
    private Integer brandId;
    private Integer categoryId;
    private String mainImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
