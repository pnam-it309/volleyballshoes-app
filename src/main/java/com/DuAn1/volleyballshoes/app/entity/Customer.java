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
public class Customer {

    private Integer customerId;
    private String phone;
    private String fullName;
    private String membershipTier;
    private Double totalSpent;
    private LocalDateTime lastPurchase;
    private Integer points;
    private LocalDateTime createdAt;
}
