package com.SeeTohJJ.Backend.user.constant;

public class UserConstant {

    public static final String INSERT_USER_PROFILE = """
        INSERT INTO user_profiles (user_id, username, gender, age, employment_status, income, country)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String GET_CURRENT_LOGIN_STREAK = """
        SELECT current_streak
        FROM user_activity
        WHERE user_id = ?
        """;

    public static final String GET_LAST_LOGIN_DATE = """
        SELECT last_login_date
        FROM user_activity
        WHERE user_id = ?
        """;

    public static final String UPDATE_LOGIN_STREAK = """
        UPDATE user_activity
        SET current_streak = ?, last_login_date = ?
        WHERE user_id = ?
        """;

    public static final String INSERT_LOGIN_STREAK = """
        INSERT INTO user_activity (user_id)
        VALUES (?)
        """;

    public static final String GET_ACTIVE_USER_COUNT = """
        SELECT count(*)
        FROM users
        WHERE role = 'USER'
        """;

    public static final String GET_ACTIVE_ADMIN_COUNT = """
        SELECT count(*)
        FROM users
        WHERE role = 'ADMIN'
        """;

    public static final String GET_NAME_FROM_ID = """
        SELECT username
        FROM user_profiles
        WHERE user_id = ?
        """;
}
