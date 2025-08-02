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
    private String customerCode;
    private String customerUsername;
    private String customerEmail;
    private String customerPhone;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Additional getters and setters for compatibility
    public String getCustomerSdt() {
        return customerPhone;
    }
    
    public void setCustomerSdt(String customerSdt) {
        this.customerPhone = customerSdt;
    }
    

}
