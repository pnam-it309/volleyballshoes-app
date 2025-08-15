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
public class Staff {

    private Integer staffId;
    private String staffUsername;
    private String staffPassword;
    private String staffEmail;
    private int staffRole;
    private String staffSdt;
    private String staffCode;
    private int staff_status;
}
