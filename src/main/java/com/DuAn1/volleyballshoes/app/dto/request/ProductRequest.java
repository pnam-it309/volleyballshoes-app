package com.DuAn1.volleyballshoes.app.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    public int brand_id;
    public int category_id;
    public String product_name;
    public String product_desc;
} 