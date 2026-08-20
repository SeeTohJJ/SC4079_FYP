package com.SeeTohJJ.Backend.auth.dao.impl;

import com.SeeTohJJ.Backend.auth.constant.PasswordResetConstant;
import com.SeeTohJJ.Backend.auth.dao.PasswordResetDao;
import com.SeeTohJJ.Backend.auth.model.PasswordReset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class PasswordResetDaoImpl implements PasswordResetDao {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public PasswordResetDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PasswordReset> passwordResetRowMapper =
            (rs, rowNum) -> {

                PasswordReset reset = new PasswordReset();

                reset.setResetId(rs.getLong("reset_id"));
                reset.setUserId(rs.getLong("user_id"));
                reset.setOtpHash(rs.getString("otp_hash"));
                reset.setResetToken(rs.getString("reset_token"));
                Timestamp expiresAt = rs.getTimestamp("expires_at");

                if (expiresAt != null) {
                    reset.setExpiresAt(expiresAt.toLocalDateTime());
                }

                reset.setOtpVerified(rs.getBoolean("otp_verified"));
                reset.setUsed(rs.getBoolean("used"));
                reset.setAttemptCount(rs.getInt("attempt_count"));
                Timestamp createdAt = rs.getTimestamp("created_at");

                if (createdAt != null) {
                    reset.setCreatedAt(createdAt.toLocalDateTime());
                }

                return reset;
            };

    @Override
    public void createPasswordReset(PasswordReset passwordReset) {
        logger.info("Starting createPasswordReset");

        jdbcTemplate.update(
                PasswordResetConstant.INSERT_PASSWORD_RESET,
                passwordReset.getUserId(),
                passwordReset.getOtpHash(),
                passwordReset.getResetToken(),
                Timestamp.valueOf(passwordReset.getExpiresAt()),
                passwordReset.isOtpVerified(),
                passwordReset.isUsed(),
                passwordReset.getAttemptCount()
        );
    }

    @Override
    public Optional<PasswordReset> getLatestActiveReset(Long userId) {
        logger.info("Starting getLatestActiveReset");

        return jdbcTemplate.query(
                PasswordResetConstant.GET_LAST_ACTIVE_RESET,
                passwordResetRowMapper,
                userId
        ).stream().findFirst();
    }

    @Override
    public void incrementAttemptCount(Long resetId) {
        logger.info("Starting incrementAttemptCount");

        jdbcTemplate.update(
                PasswordResetConstant.INCREMENT_ATTEMPT_COUNT,
                resetId
        );
    }

    @Override
    public void markOtpVerified(Long resetId, String resetToken) {
        logger.info("Starting markOtpVerified");

        jdbcTemplate.update(
                PasswordResetConstant.SET_OTP_VERIFIED,
                resetToken,
                resetId
        );
    }

    @Override
    public Optional<PasswordReset> getValidResetByToken(String resetToken) {
        logger.info("Starting getValidResetByToken {}", resetToken);

        return jdbcTemplate.query(
                PasswordResetConstant.GET_VALID_RESET_TOKEN,
                passwordResetRowMapper,
                resetToken
        ).stream().findFirst();
    }

    @Override
    public void markResetUsed(Long resetId) {
        logger.info("Starting markResetUsed");

        jdbcTemplate.update(
                PasswordResetConstant.SET_USED_TO_TRUE,
                resetId
        );
    }

    @Override
    public void invalidateAllUserResets(Long userId) {
        logger.info("Starting invalidateAllUserResets");

        jdbcTemplate.update(
                PasswordResetConstant.INVALIDATE_USER_RESET,
                userId
        );
    }
}
