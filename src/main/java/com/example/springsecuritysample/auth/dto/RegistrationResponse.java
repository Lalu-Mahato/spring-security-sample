package com.example.springsecuritysample.auth.dto;

import java.util.Date;

import com.example.springsecuritysample.user.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private boolean status;
    private Date createdAt;
    private Date updatedAt;
}
