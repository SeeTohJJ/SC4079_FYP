package com.SeeTohJJ.Backend.auth.model;

import java.time.LocalDateTime;

public class PasswordReset {

    private Long resetId;
    private Long userId;
    private String otpHash;
    private String resetToken;
    private LocalDateTime expiresAt;
    private boolean otpVerified;
    private boolean used;
    private int attemptCount;
    private LocalDateTime createdAt;

    public Long getResetId() {
        return resetId;
    }

    public void setResetId(Long resetId) {
        this.resetId = resetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOtpHash() {
        return otpHash;
    }

    public void setOtpHash(String otpHash) {
        this.otpHash = otpHash;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
