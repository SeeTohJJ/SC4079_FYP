package com.SeeTohJJ.Backend.auth.service;

public interface MailService {

    void sendPasswordResetOtp(String recipientEmail, String otp);
}
