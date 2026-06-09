package com.SeeTohJJ.Backend.user.constant;

public class UserConstant {

    public static final String INSERT_USER_PROFILE = """
        INSERT INTO user_profiles (user_id, username, gender, age, employment_status, income, country)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

}
