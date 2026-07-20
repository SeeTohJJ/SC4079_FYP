package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.topic.dao.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import com.SeeTohJJ.Backend.topic.model.UserSubTopicMastery;
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
                TopicConstant.INSERT_USER_INITIAL_TOPIC_PROGRESS,
                userId,
                topicId,
                1000,
                0
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

    @Override
    public BktParameters getBktParameters(String nodeId){
        logger.info("Starting getBktParameters");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_BKT_PARAMETERS,
                (rs, rowNum) -> {
                    BktParameters bkt = new BktParameters();
                    bkt.setSubTopic_id(rs.getString("subtopic_id"));
                    bkt.setP_init(rs.getDouble("p_init"));
                    bkt.setP_transit(rs.getDouble("p_transit"));
                    bkt.setP_slip(rs.getDouble("p_slip"));
                    bkt.setP_guess(rs.getDouble("p_guess"));
                    return bkt;
                },
                nodeId
        );
    }

    @Override
    public double getUserPKnow(Long userId, String  subTopicId) {
        logger.info("Starting getUserPKnow");

        Double result = jdbcTemplate.queryForObject(
                TopicConstant.GET_USER_P_KNOW,
                double.class,
                userId,
                subTopicId
        );
        return result != null ? result : 0.0;
    }

    @Override
    public void updatePKnow(Long userId, String subTopicId, double updatedPKnow){
        logger.info("Starting  updatePKnow");

        jdbcTemplate.update(
                TopicConstant.UPDATE_USER_P_KNOW,
                updatedPKnow,
                userId,
                subTopicId
        );
    }

    @Override
    public UserSubTopicMastery getUserAttemptHistory(Long userId, String subTopicId){
        logger.info("Starting getUserAttemptHistory");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_USER_ATTEMPT_HISTORY,
                (rs, rowNum) -> {
                    UserSubTopicMastery mastery = new UserSubTopicMastery();
                    mastery.setUserId(userId);
                    mastery.setSubtopicId(subTopicId);
                    mastery.setAttemptCount(rs.getInt("attempt_count"));
                    mastery.setCorrectCount(rs.getInt("correct_count"));
                    mastery.setWrongCount(rs.getInt("wrong_count"));
                    return mastery;
                },
                userId,
                subTopicId
        );
    }


    @Override
    public void updateCorrectAttempt(Long userId, String subTopicId, int newCorrectAttempts, int newTotalAttempts){
        logger.info("Starting updateCorrectAttempt");

        jdbcTemplate.update(
                TopicConstant.UPDATE_USER_CORRECT_ATTEMPT_HISTORY,
                newTotalAttempts,
                newCorrectAttempts,
                userId,
                subTopicId
        );
    }

    @Override
    public void updateWrongAttempt(Long userId, String subTopicId, int newWrongAttempts, int newTotalAttempts){
        logger.info("Starting updateWrongAttempt");

        jdbcTemplate.update(
                TopicConstant.UPDATE_USER_WRONG_ATTEMPT_HISTORY,
                newTotalAttempts,
                newWrongAttempts,
                userId,
                subTopicId
        );
    }

    @Override
    public double getUserElo(Long userId, String subtopicId){
        logger.info("Starting getUserElo");

        Double result = jdbcTemplate.queryForObject(
                TopicConstant.GET_USER_ELO,
                double.class,
                userId,
                subtopicId
        );

        return result != null ? result : 0.0;
    }

    @Override
    public void setUserNewEloRating(Long userId, String subtopicId, double newElo){
        logger.info("Starting setUserNewEloRating");

        jdbcTemplate.update(
                TopicConstant.SET_USER_NEW_ELO_RATING,
                newElo,
                userId,
                subtopicId
        );
    }

    @Override
    public void setIsMasteredToTrue(Long userId, String subtopicId){
        logger.info("Starting setIsMasteredToTrue");

        jdbcTemplate.update(
                TopicConstant.SET_IS_MASTERED_TO_TRUE,
                userId,
                subtopicId
        );
    }

    @Override
    public boolean isSubtopicMastered(Long userId, String subtopicId){
        logger.info("Starting isSubtopicMastered");

        Boolean result = jdbcTemplate.queryForObject(
                TopicConstant.CHECK_SUBTOPIC_MASTERED,
                boolean.class,
                userId,
                subtopicId
        );

        return Boolean.TRUE.equals(result);
    }

    @Override
    public String getTopicIdFromSubtopicId(String subtopicId){
        logger.info("Starting getTopicIdFromSubtopicId");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_TOPIC_ID_FROM_SUBTOPIC_ID,
                String.class,
                subtopicId
        );
    }

    @Override
    public boolean checkSubtopicExist(String subtopicId){
        logger.info("Starting checkSubtopicExist");

        Boolean result = jdbcTemplate.queryForObject(
                TopicConstant.CHECK_SUBTOPIC_EXIST,
                boolean.class,
                subtopicId
        );

        return Boolean.TRUE.equals(result);
    }

    @Override
    public String getRandomUninterestedTopic(Long userId){
        logger.info("Starting getRandomUninterestedTopic");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_RANDOM_UNINTERESTED_TOPIC,
                String.class,
                userId
        );
    }

    @Override
    public void insertNewSubtopicMastery(Long userId, String subtopicId, float p_know){
        logger.info("Starting insertNewSubtopicMastery");

        logger.info(userId + "-" + subtopicId + "-" + p_know);

        jdbcTemplate.update(
                TopicConstant.INSERT_NEW_SUBTOPIC_MASTERY,
                userId,
                subtopicId,
                p_know
        );
    }


}
