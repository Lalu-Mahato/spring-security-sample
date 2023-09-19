package com.example.springsecuritysample.auth.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String body;
    private String statusCode;
    private Integer statusCodeValue;

    public ErrorResponse(String body, String statusCode, Integer statusCodeValue) {
        this.body = body;
        this.statusCode = statusCode;
        this.statusCodeValue = statusCodeValue;
    }
}
