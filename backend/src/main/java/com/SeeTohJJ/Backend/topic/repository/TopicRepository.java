package com.SeeTohJJ.Backend.topic.repository;

import com.SeeTohJJ.Backend.topic.model.Topic;
import org.springframework.jdbc.core.JdbcTemplate;

public class TopicRepository {

    private final JdbcTemplate jdbc;

    public TopicRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
}
