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
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    private Integer promotionId;
    private String name;
    private String description;
    private String discountType; 
    private Double discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double minOrderAmount;
    private LocalDateTime createdAt;
}
