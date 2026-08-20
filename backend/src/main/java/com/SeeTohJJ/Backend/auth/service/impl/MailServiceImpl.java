package com.SeeTohJJ.Backend.auth.service.impl;

import com.SeeTohJJ.Backend.auth.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetOtp(String recipientEmail, String otp) {
        logger.info("Sending password reset OTP email to " + recipientEmail);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(senderEmail);
        message.setTo(recipientEmail);
        message.setSubject("Password Reset Code");
        message.setText(
                "Your password reset code is: "
                        + otp
                        + "\n\n"
                        + "This code will expire in 10 minutes."
                        + "\n\n"
                        + "If you did not request a password reset, "
                        + "you can ignore this email."
        );

        mailSender.send(message);
    }
}
