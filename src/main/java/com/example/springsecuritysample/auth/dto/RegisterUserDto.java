package com.example.springsecuritysample.auth.dto;

import com.example.springsecuritysample.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
