package com.DuAn1.volleyballshoes.app.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    private int brandId;
    private String brandName;
    private String brandCode;
    private String brandOrigin;
}
