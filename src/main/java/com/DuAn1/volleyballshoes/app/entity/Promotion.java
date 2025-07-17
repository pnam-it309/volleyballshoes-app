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
import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    private int promotionId;
    private String promoName;
    private String promoDesc;
    private double promoDiscountValue;
    private LocalDateTime promoStartDate;
    private LocalDateTime promoEndDate;
    private String promoCode;
}
