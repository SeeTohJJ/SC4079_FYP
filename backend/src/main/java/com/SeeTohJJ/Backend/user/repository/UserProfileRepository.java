package com.SeeTohJJ.Backend.user.repository;

import com.SeeTohJJ.Backend.user.model.UserProfile;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class UserProfileRepository {

    private final JdbcTemplate jdbc;

    public UserProfileRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
