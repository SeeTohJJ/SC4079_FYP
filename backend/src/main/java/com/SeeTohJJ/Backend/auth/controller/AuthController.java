package com.SeeTohJJ.Backend.auth.controller;

import com.SeeTohJJ.Backend.auth.dto.LoginRequestDTO;
import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public String register(@RequestBody RegisterRequestDTO request) {
        logger.info("Starting Register");

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        logger.info("Starting Login");

        return authService.login(request);
    }

//    @PostMapping("/forgot-password")
//    public String forgotPassword(@RequestParam String email) {
//        logger.info("Starting Forgot Password");
//
//        return authService.forgotPassword(email);
//    }
//
//    @PostMapping("/reset-password")
//    public String resetPassword(
//            @RequestParam String token,
//            @RequestParam String newPassword
//    ) {
//        logger.info("Starting Reset Password");
//
//        return authService.resetPassword(token, newPassword);
//    }

    @PostMapping("/admin/login")
    public AuthResponseDTO adminLogin(@RequestBody LoginRequestDTO request) {
        logger.info("Starting adminLogin");

        return authService.adminLogin(request);
    }
}
