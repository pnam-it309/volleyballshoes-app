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
    private String customerFullName;
    private String customerEmail;
    private String customerPhone;
    private String customerCode;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Additional getters and setters for compatibility
    public String getCustomerPhone() {
        return customerPhone != null ? customerPhone : customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        if (this.customerPhone == null) {
            this.customerPhone = customerPhone;
        }
    }
    

}
