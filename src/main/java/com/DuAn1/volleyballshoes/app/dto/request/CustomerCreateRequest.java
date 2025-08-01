package com.DuAn1.volleyballshoes.app.dto.request;

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
    private String customerSdt;

    /**
     * Validates the customer creation request.
     *
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
        if ((customerSdt == null || customerSdt.trim().isEmpty())
                && (customerSdt == null || customerSdt.trim().isEmpty())) {
            return false;
        }

        // Email is optional but if provided, must be valid
        if (customerEmail != null && !customerEmail.trim().isEmpty()) {
            if (!isValidEmail(customerEmail)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates an email address using a simple regex pattern.
     *
     * @param email the email to validate
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Simple email validation pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * Gets a user-friendly error message for validation failures.
     *
     * @return A string describing the validation error, or null if valid
     */
    public String getValidationErrorMessage() {
        if (customerCode == null || customerCode.trim().isEmpty()) {
            return "Mã khách hàng không được để trống";
        }

        if (customerFullName == null || customerFullName.trim().isEmpty()) {
            return "Họ và tên không được để trống";
        }

        if (customerEmail != null && !customerEmail.trim().isEmpty() && !isValidEmail(customerEmail)) {
            return "Địa chỉ email không hợp lệ";
        }

        return null; // No validation errors
    }
}
