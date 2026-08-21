package com.SeeTohJJ.Backend.auth.service.impl;


import com.SeeTohJJ.Backend.auth.dao.PasswordResetDao;
import com.SeeTohJJ.Backend.auth.dao.UserDao;
import com.SeeTohJJ.Backend.auth.dto.AuthResponseDTO;
import com.SeeTohJJ.Backend.auth.dto.request.LoginRequestDTO;
import com.SeeTohJJ.Backend.auth.dto.request.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.model.PasswordReset;
import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.auth.service.AuthService;
import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.auth.service.MailService;
import com.SeeTohJJ.Backend.user.service.LoginStreakService;
import com.SeeTohJJ.Backend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final LoginStreakService loginStreakService;
    private final MailService mailService;
    private final PasswordResetDao passwordResetDao;

    @Autowired
    public AuthServiceImpl(UserDao userDao,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder,
                           UserService userService,
                           LoginStreakService loginStreakService,
                           MailService mailService,
                           PasswordResetDao passwordResetDao) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.loginStreakService = loginStreakService;
        this.mailService = mailService;
        this.passwordResetDao = passwordResetDao;
    }

    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final int MAX_OTP_ATTEMPTS = 5;

    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public String register(RegisterRequestDTO request) {
        logger.info("Starting Register");

        if (userDao.findEmailExist(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPublicUserId(UUID.randomUUID());
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        Long userId = userDao.registerUser(user);

        userService.insertAllUserInitialTables(userId, request);

        return "User registered";
    }

    @Transactional
    public AuthResponseDTO login(LoginRequestDTO request) {
        logger.info("Starting Login");

        User user = userDao.findUserByEmail(request.getEmail());

        if  (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        loginStreakService.updateDailyLoginStreak(user.getUserId());

        String token = jwtService.generateToken(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());

        return response;
    }

    @Transactional
    public AuthResponseDTO adminLogin(LoginRequestDTO request) {
        logger.info("Starting adminLogin");

        User user = userDao.findUserByEmail(request.getEmail());

        if  (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());

        return response;
    }

    private String generateOtp() {

        int otp = 100000 + secureRandom.nextInt(900000);

        return String.valueOf(otp);
    }

    @Override
    public void forgotPassword(String email) {
        logger.info("Starting forgotPassword");

        Optional<User> userOptional = Optional.ofNullable(userDao.findUserByEmail(email));

        if (userOptional.isEmpty()) {
            return;
        }

        User user = userOptional.get();

        passwordResetDao.invalidateAllUserResets(user.getUserId());

        String otp = generateOtp();
        String otpHash = passwordEncoder.encode(otp);

        PasswordReset passwordReset = new PasswordReset();

        passwordReset.setUserId(user.getUserId());
        passwordReset.setOtpHash(otpHash);
        passwordReset.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        passwordReset.setOtpVerified(false);
        passwordReset.setUsed(false);
        passwordReset.setAttemptCount(0);

        passwordResetDao.createPasswordReset(passwordReset);

        mailService.sendPasswordResetOtp(user.getEmail(), otp);
    }

    @Override
    public String verifyResetOtp(String email, String otp) {

        Optional<User> userOptional = Optional.ofNullable(userDao.findUserByEmail(email));

        if (userOptional.isEmpty()) {
            throw new RuntimeException(
                    "Invalid or expired verification code"
            );
        }

        User user = userOptional.get();

        PasswordReset passwordReset = passwordResetDao.getLatestActiveReset(user.getUserId())
                                                    .orElseThrow(() -> new RuntimeException("Invalid or expired verification code"));

        if (passwordReset.getAttemptCount() >= MAX_OTP_ATTEMPTS) {
            throw new RuntimeException(
                    "Maximum verification attempts exceeded"
            );
        }

        passwordResetDao.incrementAttemptCount(passwordReset.getResetId());

        boolean matches = passwordEncoder.matches(otp, passwordReset.getOtpHash());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid verification code"
            );
        }

        String resetToken = UUID.randomUUID().toString();

        passwordResetDao.markOtpVerified(passwordReset.getResetId(), resetToken);

        return resetToken;
    }

    @Override
    public void resetPassword(String resetToken, String newPassword) {
        logger.info("Starting resetPassword");

        PasswordReset passwordReset = passwordResetDao.getValidResetByToken(resetToken)
                                                    .orElseThrow(() -> new RuntimeException("Invalid or expired reset request"));

        User user = userDao.findUserByUserId(passwordReset.getUserId());

        if (user == null) {
            throw new RuntimeException(
                    "User not found"
            );
        }

        String passwordHash = passwordEncoder.encode(newPassword);

        userDao.updatePassword(user.getUserId(), passwordHash);
        passwordResetDao.markResetUsed(passwordReset.getResetId());

        passwordResetDao.invalidateAllUserResets(user.getUserId());
    }

    @Override
    public void changePassword(String currentPassword, String newPassword){
        logger.info("Starting changePassword");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        Long userId = Long.valueOf(authentication.getName());

        User user = userDao.findUserByUserId(userId);

        if (user == null) {
            throw new RuntimeException(
                    "User not found"
            );
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException(
                    "Current password is incorrect"
            );
        }

        validatePassword(newPassword);

        String hashedPassword = passwordEncoder.encode(newPassword);

        userDao.updatePassword(user.getUserId(), hashedPassword);
    }

    private void validatePassword(String password) {

        if (password == null || password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new RuntimeException("Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new RuntimeException("Password must contain at least one lowercase letter");
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new RuntimeException("Password must contain at least one special character");
        }
    }

}
