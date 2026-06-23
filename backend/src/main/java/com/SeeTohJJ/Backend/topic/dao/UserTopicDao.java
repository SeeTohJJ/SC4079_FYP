package com.SeeTohJJ.Backend.topic.dao;

import com.SeeTohJJ.Backend.auth.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface UserTopicDao {

    void insertUserInterestedTopic(Long userId, String topicId);
    List<String> getUserTopicFromUserId(Long userId);
}
