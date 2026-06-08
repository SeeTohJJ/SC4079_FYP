package com.SeeTohJJ.Backend.topic.repository;

import com.SeeTohJJ.Backend.topic.model.UserTopicMastery;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserTopicMasteryRepository {

    private final JdbcTemplate jdbc;

    public UserTopicMasteryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
