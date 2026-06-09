package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.topic.dao.UserTopicMasteryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class UserTopicMasteryDaoImpl implements UserTopicMasteryDao {

    private static final Logger logger = LoggerFactory.getLogger(UserTopicMasteryDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserTopicMasteryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertInitialTopicMastery(Long userId, String topicId) {
        logger.info("Starting insertInitialTopicMastery");

        jdbcTemplate.update(
                TopicConstant.INSERT_USER_INITIAL_TOPIC_MASTERY,
                userId,
                topicId,
                50,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
