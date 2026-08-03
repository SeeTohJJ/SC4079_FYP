package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.UserTopicMasteryConstant;
import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.study.dao.UserTopicMasteryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

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

        try {
            jdbcTemplate.update(
                    UserTopicMasteryConstant.INSERT_USER_INITIAL_TOPIC_PROGRESS,
                    userId,
                    topicId,
                    1000,
                    0
            );
        } catch (Exception e) {
            logger.error("Error inserting initial topic progress: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String getTopUserUncompletedTopic(Long userId){
        logger.info("Starting getUserUncompletedTopic");

        try {
            return jdbcTemplate.queryForObject(
                    UserTopicMasteryConstant.GET_TOP_UNCOMPLETED_TUTORIAL_TOPIC,
                    String.class,
                    userId
            );
        } catch (Exception e) {
            logger.error("Error retrieving top uncompleted topic: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void setAverageElo(Long userId, String topicId, double averageElo){
        logger.info("Starting setAverageElo");

        try {
            jdbcTemplate.update(
                    UserTopicMasteryConstant.SET_AVERAGE_ELO,
                    averageElo,
                    userId,
                    topicId
            );
        } catch (Exception e) {
            logger.error("Error setting average ELO: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void setAveragePKnow(Long userId, String topicId, double averagePKnow){
        logger.info("Starting setAveragePKnow");

        try {
            jdbcTemplate.update(
                    UserTopicMasteryConstant.SET_AVERAGE_P_KNOW,
                    averagePKnow,
                    userId,
                    topicId
            );
        } catch (Exception e) {
            logger.error("Error setting average PKnow: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateNextReviewDate(Long userId, String topicId, LocalDate nextReview){
        logger.info("Starting updateNextReviewDate");

        try {
            jdbcTemplate.update(
                    UserTopicMasteryConstant.SET_NEXT_REVIEW_DATE,
                    nextReview,
                    userId,
                    topicId
            );
        } catch (Exception e) {
            logger.error("Error updating next review: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateIntervalDay(Long userId, String topicId, int interval){
        logger.info("Starting updateIntervalDay");

        try {
            jdbcTemplate.update(
                    UserTopicMasteryConstant.SET_REVIEW_INTERVAL_DAY,
                    interval,
                    userId,
                    topicId
            );
        } catch (Exception e) {
            logger.error("Error updating interval day: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public LocalDate getNextReview(Long userId, String topicId){
        logger.info("Starting getNextReview");

        try {
            return jdbcTemplate.queryForObject(
                    UserTopicMasteryConstant.GET_NEXT_REVIEW_DATE,
                    LocalDate.class,
                    userId,
                    topicId
            );
        } catch (Exception e) {
            logger.error("Error retrieving next review date: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<String> getDueReviews(Long userId){
        logger.info("Starting getDueReviews");

        try {
            return jdbcTemplate.queryForList(
                    UserTopicMasteryConstant.GET_DUE_REVIEWS,
                    String.class,
                    userId
            );
        } catch (Exception e) {
            logger.error("Error retrieving due reviews: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public double getAverageElo(Long userId, String topicId){
        logger.info("Starting getAverageElo");

        try {
            Double result = jdbcTemplate.queryForObject(
                    UserTopicMasteryConstant.GET_AVERAGE_ELO,
                    Double.class,
                    userId,
                    topicId
            );

            return result != null ? result : 0;
        } catch (Exception e) {
            logger.error("Error retrieving average ELO: {}", e.getMessage());
            return 0.0;
        }
    }

    @Override
    public double getAveragePKnow(Long userId, String topicId){
        logger.info("Starting getAveragePKnow");

        try {
            Double result = jdbcTemplate.queryForObject(
                    UserTopicMasteryConstant.GET_AVERAGE_P_KNOW,
                    Double.class,
                    userId,
                    topicId
            );
            return result != null ? result : 0.0;
        } catch (Exception e) {
            logger.error("Error retrieving average PKnow: {}", e.getMessage());
            return 0.0;
        }
    }


}
