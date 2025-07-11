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
    private String username;
    private String passwordHash;
    private String pinCode;
    private String fullName;
    private Integer status; // 0: staff, 1: admin
    private LocalDateTime createdAt;

    public boolean isAdmin() {
        return status == 1;
    }
}
