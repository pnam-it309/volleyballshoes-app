package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDate;
import lombok.*;

/**
 * Entity class representing a customer in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    
    private Integer customerId;
    private String customerUsername;
    private String customerPassword;
    private String customerFullName;
    private String customerEmail;
    private String customerPhone;
    private String customerSdt;
    private String customerCode;
    private Integer customerPoints;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Additional getters and setters for compatibility
    public String getCustomerSdt() {
        return customerSdt != null ? customerSdt : customerPhone;
    }
    
    public void setCustomerSdt(String customerSdt) {
        this.customerSdt = customerSdt;
        if (this.customerPhone == null) {
            this.customerPhone = customerSdt;
        }
    }
    
    // Helper method to get points with null check
    public Integer getCustomerPoints() {
        return customerPoints != null ? customerPoints : 0;
    }
    
    // Helper method to set points with null check
    public void setCustomerPoints(Integer points) {
        this.customerPoints = points != null ? points : 0;
    }
}
