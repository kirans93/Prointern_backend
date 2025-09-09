package com.example.auth.controller;

import com.example.auth.service.AuthService;
import com.example.auth.dto.AuthRequest;
import com.example.auth.dto.AuthResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private AuthService authService; // 

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request){
        return authService.signup(request.getEmail(), request.getPassword());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        String token = authService.login(request.getEmail(), request.getPassword());
        return new AuthResponse("Login successful", token);
    }


    @PostMapping("/forgot")
    public String forgot(@RequestParam String email){
        return authService.sendOtp(email);
    }

    @PostMapping("/reset")
    public String reset(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword){
        return authService.resetPassword(email, otp, newPassword);
    }
}
