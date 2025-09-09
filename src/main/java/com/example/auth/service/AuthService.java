package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MailService mailService;

    public String signup(String email, String password) {
        if (userRepository.existsByEmail(email)) throw new RuntimeException("Email exists");

        User user = new User();              // No builder needed
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return "User registered!";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Invalid password");
        return jwtUtil.generateToken(user.getEmail());
    }

    public String sendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String otp = String.format("%04d", new Random().nextInt(10000));
        user.setOtp(otp);
        userRepository.save(user);
        mailService.sendOtpEmail(email, otp);
        return "OTP sent";
    }

    public String resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!otp.equals(user.getOtp())) throw new RuntimeException("Invalid OTP");
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        userRepository.save(user);
        return "Password reset!";
    }
}
