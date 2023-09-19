package com.example.springsecuritysample.auth.dto;

import com.example.springsecuritysample.user.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String token;
}
