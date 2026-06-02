package com.SeeTohJJ.Backend.auth.controller;

import com.SeeTohJJ.Backend.auth.dto.LoginRequest;
import com.SeeTohJJ.Backend.auth.dto.AuthResponse;
import com.SeeTohJJ.Backend.auth.dto.RegisterRequest;
import com.SeeTohJJ.Backend.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        logger.info("Starting Register");

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        logger.info("Starting Login");

        return authService.login(
                req.getEmail(),
                req.getPassword()
        );
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        logger.info("Starting Forgot Password");

        return authService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        logger.info("Starting Reset Password");

        return authService.resetPassword(token, newPassword);
    }
}
