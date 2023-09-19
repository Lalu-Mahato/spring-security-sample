package com.example.springsecuritysample.user.dto;

import java.util.Date;

import com.example.springsecuritysample.user.Role;

import lombok.Data;

@Data
public class UserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    private boolean status;
    private Date createdAt;
    private Date updatedAt;
}
