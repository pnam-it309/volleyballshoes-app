package com.DuAn1.volleyballshoes.app.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private int categoryId;
    private String categoryName;
    private String categoryCode;
}

