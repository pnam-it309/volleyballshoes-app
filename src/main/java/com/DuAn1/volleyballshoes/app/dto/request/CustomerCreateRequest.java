package com.DuAn1.volleyballshoes.app.dto.request;

import com.DuAn1.volleyballshoes.app.entity.Customer.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreateRequest {
    
    private String customerCode;
    private String customerFullName;
    private String customerEmail;
    private String customerPhone;
    private String customerSdt; // Consider removing as it's redundant with customerPhone
    private Gender customerGender;
    private LocalDate customerBirthDate;
    private String customerAddress;
    
    /**
     * Validates the customer creation request.
     * @return true if all required fields are valid, false otherwise
     */
    public boolean isValid() {
        if (customerCode == null || customerCode.trim().isEmpty()) {
            return false;
        }
        
        if (customerFullName == null || customerFullName.trim().isEmpty()) {
            return false;
        }
        
        // At least one contact method is required
        if ((customerPhone == null || customerPhone.trim().isEmpty()) && 
            (customerSdt == null || customerSdt.trim().isEmpty())) {
            return false;
        }
        
        // Email is optional but if provided, must be valid
        if (customerEmail != null && !customerEmail.trim().isEmpty()) {
            if (!isValidEmail(customerEmail)) {
                return false;
            }
        }
        
        // Birth date is optional but if provided, must be in the past
        if (customerBirthDate != null && customerBirthDate.isAfter(LocalDate.now())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates an email address using a simple regex pattern.
     * @param email the email to validate
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        // Simple email validation pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    /**
     * Gets a user-friendly error message for validation failures.
     * @return A string describing the validation error, or null if valid
     */
    public String getValidationErrorMessage() {
        if (customerCode == null || customerCode.trim().isEmpty()) {
            return "Mã khách hàng không được để trống";
        }
        
        if (customerFullName == null || customerFullName.trim().isEmpty()) {
            return "Họ và tên không được để trống";
        }
        
        if ((customerPhone == null || customerPhone.trim().isEmpty()) && 
            (customerSdt == null || customerSdt.trim().isEmpty())) {
            return "Phải nhập ít nhất một số điện thoại";
        }
        
        if (customerEmail != null && !customerEmail.trim().isEmpty() && !isValidEmail(customerEmail)) {
            return "Địa chỉ email không hợp lệ";
        }
        
        if (customerBirthDate != null && customerBirthDate.isAfter(LocalDate.now())) {
            return "Ngày sinh không thể là ngày trong tương lai";
        }
        
        return null; // No validation errors
    }
}
