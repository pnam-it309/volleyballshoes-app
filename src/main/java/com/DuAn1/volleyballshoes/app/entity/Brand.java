package com.DuAn1.volleyballshoes.app.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    public int brand_id;
    public String brand_name;
    public String brand_origin;
}
