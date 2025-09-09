package com.example.auth.dto;

import lombok.Data;

@Data
public class ResetRequest {
    private String email;
    private String otp;
    private String newPassword;
}
