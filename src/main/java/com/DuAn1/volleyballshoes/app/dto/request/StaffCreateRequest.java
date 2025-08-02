package com.DuAn1.volleyballshoes.app.dto.request;

import lombok.Data;

@Data
public class StaffCreateRequest {

    private String name;

    private String email;

    private String password;

    private String phone;

    private String address;

    private String role;
}
