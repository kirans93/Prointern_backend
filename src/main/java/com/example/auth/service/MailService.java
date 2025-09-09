package com.example.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service   // ✅ This makes it a Spring-managed bean
public class MailService {

    @Autowired
    private JavaMailSender mailSender;  // ✅ Spring will inject this automatically

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message); // ✅ Now no NPE
    }
}
