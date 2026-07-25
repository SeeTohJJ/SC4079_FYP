package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.UserTopicMasteryConstant;
import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.study.dao.UserTopicMasteryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserTopicMasteryDaoImpl implements UserTopicMasteryDao {

    private static final Logger logger = LoggerFactory.getLogger(UserTopicMasteryDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserTopicMasteryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertInitialTopicProgress(Long userId, String topicId) {
        logger.info("Starting insertInitialTopicProgress");

        jdbcTemplate.update(
                UserTopicMasteryConstant.INSERT_USER_INITIAL_TOPIC_PROGRESS,
                userId,
                topicId,
                1000,
                0
        );
    }

    @Override
    public String getTopUserUncompletedTopic(Long userId){
        logger.info("Starting getUserUncompletedTopic");

        return jdbcTemplate.queryForObject(
                UserTopicMasteryConstant.GET_TOP_UNCOMPLETED_TUTORIAL_TOPIC,
                String.class,
                userId
        );
    }

    @Override
    public boolean isTutorialCompleted(Long userId, String topicId) {
        logger.info("Starting isTutorialCompleted");

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                UserTopicMasteryConstant.CHECK_TUTORIAL_COMPLETED,
                boolean.class,
                userId,
                topicId
        ));
    }
}
