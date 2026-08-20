package com.SeeTohJJ.Backend.auth.constant;

public class PasswordResetConstant {

    public static final String INSERT_PASSWORD_RESET = """
            INSERT INTO password_reset (user_id, otp_hash, reset_token, expires_at, otp_verified, used, attempt_count)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String GET_LAST_ACTIVE_RESET = """
            SELECT *
            FROM password_reset
            WHERE user_id = ? AND used = FALSE AND expires_at > CURRENT_TIMESTAMP
            ORDER BY created_at DESC
            LIMIT 1
            """;

    public static final String INCREMENT_ATTEMPT_COUNT = """
            UPDATE password_reset
            SET attempt_count = attempt_count + 1
            WHERE reset_id = ?
            """;

    public static final String SET_OTP_VERIFIED = """
            UPDATE password_reset
            SET otp_verified = TRUE, reset_token = ?
            WHERE reset_id = ?
            """;

    public static final String GET_VALID_RESET_TOKEN = """
            SELECT *
            FROM password_reset
            WHERE reset_token = ? AND otp_verified = TRUE AND used = FALSE AND expires_at > CURRENT_TIMESTAMP
            LIMIT 1
            """;

    public static final String SET_USED_TO_TRUE = """
            UPDATE password_reset
            SET used = TRUE
            WHERE reset_id = ?
            """;

    public static final String INVALIDATE_USER_RESET = """
            UPDATE password_reset
            SET used = TRUE
            WHERE user_id = ? AND used = FALSE
            """;
}
