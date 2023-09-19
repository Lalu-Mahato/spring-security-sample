package com.example.springsecuritysample.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public ResponseEntity<Object> register() {
        return ResponseEntity.ok("User Registered");
    }

    public ResponseEntity<Object> authenticate() {
        return ResponseEntity.ok("User Authentication");
    }

}
