/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

/**
 *
 * @author nickh
 */
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private int orderId;
    private Integer customerId;  // Made nullable
    private int staffId;
    private BigDecimal orderFinalAmount;
    private String orderPaymentMethod;
    private String orderStatus;
    private String orderCode;
    private LocalDateTime orderCreatedAt;
}
