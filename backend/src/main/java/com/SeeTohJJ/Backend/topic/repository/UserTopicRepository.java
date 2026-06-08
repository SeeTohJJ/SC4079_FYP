package com.SeeTohJJ.Backend.topic.repository;

import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.topic.model.UserInterestedTopic;
import com.SeeTohJJ.Backend.auth.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserTopicRepository {

    private final JdbcTemplate jdbc;

    public UserTopicRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
