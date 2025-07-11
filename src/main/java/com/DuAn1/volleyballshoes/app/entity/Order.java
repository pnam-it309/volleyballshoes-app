/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

/**
 *
 * @author nickh
 */
import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Integer orderId;
    private Integer customerId;
    private Integer staffId;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private String paymentMethod; // 'cash', 'card', 'transfer'
    private String status; // 'completed', 'cancelled', 'pending'
    private LocalDateTime createdAt;
    private String note;
}
