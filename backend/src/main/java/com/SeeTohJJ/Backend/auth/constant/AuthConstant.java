package com.SeeTohJJ.Backend.auth.constant;

public class AuthConstant {

    public static final String FIND_EMAIL_EXIST = """
        SELECT EXISTS (
            SELECT 1
            FROM users
            WHERE email = ?
        )
        """;

    public static final String INSERT_NEW_USER = """
        INSERT INTO users (email, password, role, public_user_id, created_at)
        VALUES (?, ?, ?, ?, ?)
        RETURNING user_id;
        """;

    public static final String FIND_USER_BY_EMAIL = """
        SELECT user_id, email, password, role, public_user_id, reset_token, created_at
        FROM users
        WHERE email = ?
        """;

    public static final String FIND_USER_BY_USER_ID = """
        SELECT user_id, email, password, role, public_user_id, reset_token, created_at
        FROM users
        WHERE user_id = ?
        """;

    public static final String UPDATE_USER_PASSWORD = """
        UPDATE users
        SET password = ?, reset_token = NULL
        WHERE user_id = ?
        """;

}
