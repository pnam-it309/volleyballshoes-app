/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    public int variant_id;
    public int product_id;
    public int size_id;
    public int color_id;
    public int sole_id;
    public String variant_sku;
    public double variant_orig_price;
    public String variant_img_url;
}
