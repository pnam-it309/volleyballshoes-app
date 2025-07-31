/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

import java.math.BigDecimal;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    private int variantId;
    private int productId;
    private int sizeId;
    private int colorId;
    private int soleId;
    private String variantSku;
    private BigDecimal variantOrigPrice;
    private String variantImgUrl;
}
