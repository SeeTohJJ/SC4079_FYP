package com.SeeTohJJ.Backend.auth.dao;

import com.SeeTohJJ.Backend.auth.model.PasswordReset;

import java.util.Optional;

public interface PasswordResetDao {

    void createPasswordReset(PasswordReset passwordReset);
    Optional<PasswordReset> getLatestActiveReset(Long userId);
    void incrementAttemptCount(Long resetId);
    void markOtpVerified(Long resetId, String resetToken);
    Optional<PasswordReset> getValidResetByToken(String resetToken);
    void markResetUsed(Long resetId);
    void invalidateAllUserResets(Long userId);
}
