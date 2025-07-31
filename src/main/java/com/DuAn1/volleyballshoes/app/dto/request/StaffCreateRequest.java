package com.DuAn1.volleyballshoes.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StaffCreateRequest {
    @NotBlank(message = "Tên nhân viên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @Pattern(regexp = "(^$|[0-9]{10,11})", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String address;
    
    @NotBlank(message = "Vai trò không được để trống")
    private String role;
}
