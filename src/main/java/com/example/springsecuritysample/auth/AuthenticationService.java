package com.example.springsecuritysample.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecuritysample.auth.dto.LoginResponse;
import com.example.springsecuritysample.auth.dto.LoginUserDto;
import com.example.springsecuritysample.auth.dto.RegisterUserDto;
import com.example.springsecuritysample.auth.dto.RegistrationResponse;
import com.example.springsecuritysample.config.JwtService;
import com.example.springsecuritysample.user.User;
import com.example.springsecuritysample.user.UserRepository;

@Service
public class AuthenticationService {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private AuthenticationManager authenticationManager;

        public ResponseEntity<Object> register(RegisterUserDto registerUserDto) {
                try {
                        var user = User.builder()
                                        .firstname(registerUserDto.getFirstname())
                                        .lastname(registerUserDto.getLastname())
                                        .email(registerUserDto.getEmail())
                                        .password(passwordEncoder.encode(registerUserDto.getPassword()))
                                        .role(registerUserDto.getRole())
                                        .build();

                        userRepository.save(user);
                        RegistrationResponse response = RegistrationResponse.builder()
                                        .id(user.getId())
                                        .firstname(user.getFirstname())
                                        .lastname(user.getLastname())
                                        .email(user.getEmail())
                                        .role(user.getRole())
                                        .status(user.isStatus())
                                        .createdAt(user.getCreatedAt())
                                        .updatedAt(user.getUpdatedAt())
                                        .build();

                        return new ResponseEntity<>(response, HttpStatus.CREATED);
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        public ResponseEntity<Object> authenticate(LoginUserDto loginUserDto) {
                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        loginUserDto.getEmail(), loginUserDto.getPassword()));

                        var user = userRepository.findByEmail(loginUserDto.getEmail())
                                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                        var token = jwtService.generateToken(user);

                        LoginResponse response = LoginResponse.builder()
                                        .id(user.getId())
                                        .firstname(user.getFirstname())
                                        .lastname(user.getLastname())
                                        .email(user.getEmail())
                                        .role(user.getRole())
                                        .status(user.isStatus())
                                        .token(token)
                                        .createdAt(user.getCreatedAt())
                                        .updatedAt(user.getUpdatedAt())
                                        .build();
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
                }
        }
}
