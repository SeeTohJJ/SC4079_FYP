package com.SeeTohJJ.Backend.auth.controller;

import com.SeeTohJJ.Backend.auth.dto.request.*;
import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequestDTO request) {
        logger.info("Starting Register");

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        logger.info("Starting Login");

        return authService.login(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        logger.info("Starting forgotPassword");

        authService.forgotPassword(request.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "If an account exists for this email, "
                                + "a verification code has been sent."
                )
        );
    }

    @PostMapping("/verify-reset-otp")
    public ResponseEntity<?> verifyResetOtp(@RequestBody VerifyResetOTPRequestDTO request) {
        logger.info("Starting verifyResetOtp");

        String resetToken = authService.verifyResetOtp(request.getEmail(), request.getOtp());
        logger.info("Reset OTP: " + resetToken);
        return ResponseEntity.ok(
                Map.of(
                        "resetToken",
                        resetToken
                )
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        logger.info("Starting resetPassword {}, {}",  request.getResetToken(), request.getNewPassword());

        authService.resetPassword(request.getResetToken(), request.getNewPassword());

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Password successfully reset"
                )
        );
    }

    @PostMapping("/admin/login")
    public AuthResponseDTO adminLogin(@RequestBody LoginRequestDTO request) {
        logger.info("Starting adminLogin");

        return authService.adminLogin(request);
    }
}
