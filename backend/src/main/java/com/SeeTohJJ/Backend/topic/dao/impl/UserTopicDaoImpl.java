package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.topic.dao.UserTopicDao;
import com.SeeTohJJ.Backend.topic.model.UserInterestedTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import com.SeeTohJJ.Backend.topic.constant.TopicConstant;

import java.time.LocalDateTime;


@Repository
public class UserTopicDaoImpl implements UserTopicDao {

    private static final Logger logger = LoggerFactory.getLogger(UserTopicDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserTopicDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertUserInterestedTopic(Long userId, String topicId){
        logger.info("Starting insertUserInterestedTopic");
        jdbcTemplate.update(
                TopicConstant.INSERT_USER_INTERESTED_TOPIC,
                userId,
                topicId,
                UserInterestedTopic.Status.ACTIVE.name(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
