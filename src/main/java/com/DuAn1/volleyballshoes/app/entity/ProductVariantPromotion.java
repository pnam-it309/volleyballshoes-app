/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nickh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantPromotion {
    private int productVariantId;
    private int promotionId;
    private boolean isActive;
    private BigDecimal discountedPrice;
}
