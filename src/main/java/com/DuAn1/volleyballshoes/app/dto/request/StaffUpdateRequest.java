package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StaffUpdateRequest {
    private String name;
    private String phone;
    private String address;
    private String role;
    private Boolean active;

    @Pattern(regexp = "(^$|[0-9]{10,11})", message = "Số điện thoại không hợp lệ")
    public String getPhone() {
        return phone;
    }
}
