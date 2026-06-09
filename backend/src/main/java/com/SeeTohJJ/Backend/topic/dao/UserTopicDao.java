package com.SeeTohJJ.Backend.topic.dao;

import com.SeeTohJJ.Backend.auth.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

public interface UserTopicDao {

    void insertUserInterestedTopic(Long userId, String topicId);
}
