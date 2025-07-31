/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDate;

/**
 *
 * @author nickh
 */
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    public enum Gender {
        MALE("Nam"),
        FEMALE("Nữ"),
        OTHER("Khác");
        
        private final String displayName;
        
        Gender(String displayName) {
            this.displayName = displayName;
        }
        
        public String getGenderDisplayName() {
            return displayName;
        }
        
        public static Gender fromDisplayName(String displayName) {
            if (displayName == null) return null;
            for (Gender gender : values()) {
                if (gender.displayName.equals(displayName)) {
                    return gender;
                }
            }
            return null;
        }
    }

    private int customerId;
    private String customerUsername;
    private String customerPassword;
    private String customerFullName;
    private String customerEmail;
    private String customerPhone;
    private String customerSdt; // Consider removing this field as it's redundant with customerPhone
    private String customerCode;
    private int customerPoints;
    private Gender customerGender;
    private LocalDate customerBirthDate;
    private String customerAddress;

    // Helper method to get gender display name
    public String getGenderDisplayName() {
        return customerGender != null ? customerGender.getGenderDisplayName() : "";
    }

    // Helper method to get gender from display name (kept for backward compatibility)
    public static Gender getGenderFromDisplayName(String displayName) {
        return Gender.fromDisplayName(displayName);
    }
}
