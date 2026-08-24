package com.SeeTohJJ.Backend.user.dao.impl;

import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.user.constant.UserInterestedTopicsConstant;
import com.SeeTohJJ.Backend.user.dao.UserInterestedTopicsDao;
import com.SeeTohJJ.Backend.user.model.UserInterestedTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class UserInterestedTopicsDaoImpl implements UserInterestedTopicsDao {

    private static final Logger logger = LoggerFactory.getLogger(UserInterestedTopicsDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserInterestedTopicsDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertUserInterestedTopic(Long userId, String topicId){
        logger.info("Starting insertUserInterestedTopic");

        jdbcTemplate.update(
                UserInterestedTopicsConstant.INSERT_USER_INTERESTED_TOPIC,
                userId,
                topicId,
                UserInterestedTopic.Status.ACTIVE.name(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Override
    public List<String> getUserTopicFromUserId(Long userId) {
        logger.info("Starting getUserTopicFromUserId");

        return jdbcTemplate.queryForList(
                UserInterestedTopicsConstant.GET_USER_TOPIC_FROM_USERID,
                String.class,
                userId
        );
    }

    @Override
    public String getRandomUninterestedTopic(Long userId){
        logger.info("Starting getRandomUninterestedTopic");

        return jdbcTemplate.queryForObject(
                UserInterestedTopicsConstant.GET_RANDOM_UNINTERESTED_TOPIC,
                String.class,
                userId
        );
    }

    @Override
    public void completeTutorialForInterestedTopic(Long userId, String subtopicId){
        logger.info("Starting completeTutorialForInterestedTopic");

        try {
            jdbcTemplate.update(
                    UserInterestedTopicsConstant.COMPLETE_TUTORIAL_FOR_INTERESTED_TOPIC,
                    userId,
                    subtopicId
            );
        } catch (Exception e) {
            logger.error("Error completing tutorial for interested topic: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Topic> getInterestedTopicsByUserId(Long userId){
        logger.info("Starting getInterestedTopicsByUserId");

        try{
            return jdbcTemplate.query(
                UserInterestedTopicsConstant.GET_INTERESTED_TOPICS_BY_USER_ID,
                    (rs, rowNum) -> {
                        Topic topic = new Topic();
                        topic.setTopicId(rs.getString("topic_id"));
                        topic.setName(rs.getString("topic_name"));
                        return topic;
                    },
                    userId
            );
        } catch (Exception e){
            logger.error("Error retrieving interested topics by user ID: {}", e.getMessage());
            throw e;
        }
    }
}
