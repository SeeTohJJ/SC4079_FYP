package com.SeeTohJJ.Backend.auth.service;

import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.dto.request.LoginRequestDTO;
import com.SeeTohJJ.Backend.auth.dto.request.RegisterRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String register(RegisterRequestDTO request);
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO adminLogin(LoginRequestDTO request);

    void forgotPassword(String email);
    String verifyResetOtp(String email, String otp);
    void resetPassword(String resetToken, String newPassword);
}
