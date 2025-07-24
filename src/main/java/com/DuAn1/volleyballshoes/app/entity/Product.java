package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    public int product_id;
    public int brand_id;
    public int category_id;
    public String product_name;
    public String product_desc;
    public LocalDateTime product_create_at;
    public LocalDateTime product_updated_at;
}
