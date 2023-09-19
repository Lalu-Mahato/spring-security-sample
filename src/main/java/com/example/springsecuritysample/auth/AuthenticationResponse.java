package com.example.springsecuritysample.auth;

import com.example.springsecuritysample.user.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private String token;
}
