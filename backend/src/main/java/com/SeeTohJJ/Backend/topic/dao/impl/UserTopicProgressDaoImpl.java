package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.topic.dao.UserTopicProgressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Boolean.FALSE;

@Repository
public class UserTopicProgressDaoImpl implements UserTopicProgressDao {

    private static final Logger logger = LoggerFactory.getLogger(UserTopicProgressDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserTopicProgressDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertInitialTopicProgress(Long userId, String topicId) {
        logger.info("Starting insertInitialTopicProgress");

        jdbcTemplate.update(
                TopicConstant.INSERT_USER_INITIAL_TOPIC_PROGRESS,
                userId,
                topicId,
                50,
                1000,
                0,
                FALSE,
                "NULL",
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    public boolean isTutorialCompleted(Long userId, String topicId) {
        logger.info("Starting isTutorialCompleted");

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                TopicConstant.CHECK_TUTORIAL_COMPLETED,
                boolean.class,
                userId,
                topicId
        ));
    }

    @Override
    public String getTopUserUncompletedTopic(Long userId){
        logger.info("Starting getUserUncompletedTopic");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_TOP_UNCOMPLETED_TUTORIAL_TOPIC,
                String.class,
                userId
        );
    }
}
