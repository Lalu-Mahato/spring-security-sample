package com.example.springsecuritysample.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        public RegistrationResponse register(RegisterUserDto registerUserDto) {
                var user = User.builder()
                                .firstname(registerUserDto.getFirstname())
                                .lastname(registerUserDto.getLastname())
                                .email(registerUserDto.getEmail())
                                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                                .role(registerUserDto.getRole())
                                .build();

                userRepository.save(user);
                return RegistrationResponse.builder()
                                .id(user.getId())
                                .firstname(user.getFirstname())
                                .lastname(user.getLastname())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build();
        }

        public LoginResponse authenticate(LoginUserDto loginUserDto) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginUserDto.getEmail(), loginUserDto.getPassword()));

                var user = userRepository.findByEmail(loginUserDto.getEmail())
                                .orElseThrow();
                var token = jwtService.generateToken(user);

                return LoginResponse.builder()
                                .id(user.getId())
                                .firstname(user.getFirstname())
                                .lastname(user.getLastname())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .token(token)
                                .build();
        }

}
