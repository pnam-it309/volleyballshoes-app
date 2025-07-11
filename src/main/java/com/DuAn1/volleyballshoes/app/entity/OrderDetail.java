/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

/**
 *
 * @author nickh
 */
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    private Integer orderDetailId;
    private Integer orderId;
    private Integer variantId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
}
