package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.StudyConstant;
import com.SeeTohJJ.Backend.study.constant.UserSubtopicMasteryConstant;
import com.SeeTohJJ.Backend.study.constant.UserTopicMasteryConstant;
import com.SeeTohJJ.Backend.study.dao.UserSubtopicMasteryDao;
import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.topic.model.UserSubTopicMastery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class UserSubtopicMasteryDaoImpl implements UserSubtopicMasteryDao {

    private static final Logger logger = LoggerFactory.getLogger(UserSubtopicMasteryDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserSubtopicMasteryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public double getUserPKnow(Long userId, String  subTopicId) {
        logger.info("Starting getUserPKnow");

        try{
            Double result = jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_USER_P_KNOW,
                    double.class,
                    userId,
                    subTopicId
            );
            return result != null ? result : 0.0;
        }
        catch(EmptyResultDataAccessException e){
            logger.warn("No result found for userId: {} and subTopicId: {}", userId, subTopicId);
            return 0.0;
        }

    }

    @Override
    public void saveUserPKnow(Long userId, String subTopicId, double updatedPKnow){
        logger.info("Starting saveUserPKnow");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.UPDATE_USER_P_KNOW,
                    updatedPKnow,
                    userId,
                    subTopicId
            );
        }
        catch(EmptyResultDataAccessException e){
            logger.warn("No result found for userId: {} and subTopicId: {}.", userId, subTopicId);
        }
    }

    @Override
    public double getUserElo(Long userId, String subtopicId){
        logger.info("Starting getUserElo");

        try {
            Double result = jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_USER_ELO,
                    double.class,
                    userId,
                    subtopicId
            );

            return result != null ? result : 0.0;
        }
        catch(EmptyResultDataAccessException e){
            logger.warn("No Elo found for userId: {} and subtopicId: {}", userId, subtopicId);
            return 0.0;
        }
    }

    @Override
    public void setUserElo(Long userId, String subtopicId, double newElo){
        logger.info("Starting setUserElo");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.SET_USER_NEW_ELO_RATING,
                    newElo,
                    userId,
                    subtopicId
            );
        }
        catch(EmptyResultDataAccessException e) {
            logger.warn("No result found for userId: {} and subtopicId: {}.", userId, subtopicId);
        }
    }

    @Override
    public void setSubtopicIsMasteredToTrue(Long userId, String subtopicId){
        logger.info("Starting setIsMasteredToTrue");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.SET_IS_MASTERED_TO_TRUE,
                    userId,
                    subtopicId
            );
        }
        catch(EmptyResultDataAccessException e) {
            logger.warn("No subtopic isMastered found for userId: {} and subtopicId: {}", userId, subtopicId);
        }
    }

    @Override
    public void setSubtopicIsMasteredToFalse(Long userId, String subtopicId){
        logger.info("Starting setSubtopicIsMasteredToFalse");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.SET_IS_MASTERED_TO_FALSE,
                    userId,
                    subtopicId
            );
        }
        catch(EmptyResultDataAccessException e) {
            logger.warn("No subtopic isMastered found for userId: {} and subtopicId: {}", userId, subtopicId);
        }
    }

    @Override
    public boolean isSubtopicMastered(Long userId, String subtopicId) {
        logger.info("Starting isSubtopicMastered");

        try {
            Boolean result = jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.CHECK_SUBTOPIC_MASTERED,
                    boolean.class,
                    userId,
                    subtopicId
            );
            return Boolean.TRUE.equals(result);
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No subtopic isMastered found for userId: {} and subtopicId: {}", userId, subtopicId);
            return false;
        }
    }

    // TODO figure out what this is for
    @Override
    public void insertNewSubtopicMastery(Long userId, String subtopicId, double p_know){
        logger.info("Starting insertNewSubtopicMastery");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.INSERT_NEW_SUBTOPIC_MASTERY,
                    userId,
                    subtopicId,
                    p_know
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("Subtopic Mastery already exists for userId: {} and subtopicId: {}.", userId, subtopicId);
        }
    }

    @Override
    public UserSubTopicMastery getUserAttemptHistory(Long userId, String subTopicId){
        logger.info("Starting getUserAttemptHistory");

        try {
            return jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_USER_ATTEMPT_HISTORY,
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
        catch (EmptyResultDataAccessException e) {
            logger.warn("No attempt history found for userId: {} and subTopicId: {}", userId, subTopicId);
            return null;
        }
    }

    @Override
    public void updateCorrectAttempt(Long userId, String subTopicId, int newCorrectAttempts, int newTotalAttempts){
        logger.info("Starting updateCorrectAttempt");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.UPDATE_USER_CORRECT_ATTEMPT_HISTORY,
                    newTotalAttempts,
                    newCorrectAttempts,
                    userId,
                    subTopicId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No attempt history found for userId: {} and subTopicId: {}", userId, subTopicId);
        }
    }

    @Override
    public void updateWrongAttempt(Long userId, String subTopicId, int newWrongAttempts, int newTotalAttempts){
        logger.info("Starting updateWrongAttempt");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.UPDATE_USER_WRONG_ATTEMPT_HISTORY,
                    newTotalAttempts,
                    newWrongAttempts,
                    userId,
                    subTopicId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No attempt history found for userId: {} and subTopicId: {}", userId, subTopicId);
        }
    }

    @Override
    public String getLowestPKnowSubtopic(Long userId){
        logger.info("Starting getLowestPKnowSubtopic");

        try {
            return jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_LOWEST_P_KNOW_SUBTOPIC,
                    (rs, rowNum) -> rs.getString("subtopic_id"),
                    userId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No subtopic found for userId: {}", userId);
            return null;
        }
    }

    @Override
    public String getLowestPKnowSubtopicNotMastered(Long userId){
        logger.info("Starting getLowestPKnowSubtopicNotMastered");

        try {
            return jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_LOWEST_P_KNOW_SUBTOPIC_NOT_MASTERED,
                    (rs, rowNum) -> rs.getString("subtopic_id"),
                    userId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No subtopic found for userId: {}", userId);
            return null;
        }
    }

    @Override
    public int getCurrentChain(Long userId, String subtopicId){
        logger.info("Starting getCurrentChain");

        try {
            return jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_USER_SUBTOPIC_CURRENT_CHAIN,
                    (rs, rowNum) -> rs.getInt("current_chain"),
                    userId,
                    subtopicId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No current chain found for userId: {} and subtopicId: {}", userId, subtopicId);
            return 0;
        }
    }

    @Override
    public LocalDateTime getLastUpdated(Long userId, String subtopicId){
        logger.info("Starting getLastUpdated");

        try {
            return jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.GET_USER_SUBTOPIC_LAST_UPDATED,
                    (rs, rowNum) -> rs.getTimestamp("last_updated").toLocalDateTime(),
                    userId,
                    subtopicId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No last updated timestamp found for userId: {} and subtopicId: {}", userId, subtopicId);
            return null;
        }
    }

    @Override
    public void setUserSubtopicPKnow(Long userId, String subtopicId, double PKnow){
        logger.info("Starting setUserSubtopicPKnow");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.SET_USER_SUBTOPIC_P_KNOW,
                    PKnow,
                    userId,
                    subtopicId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No result found for userId: {} and subtopicId: {}.", userId, subtopicId);
        }
    }

    @Override
    public void incrementHintUsage(Long userId, String subtopicId){
        logger.info("Starting incrementHintUsage");

        try {
            jdbcTemplate.update(
                    UserSubtopicMasteryConstant.INCREMENT_HINT_USAGE,
                    userId,
                    subtopicId
            );
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No result found for userId: {} and subtopicId: {}.", userId, subtopicId);
        }
    }

    @Override
    public boolean isTutorialCompleted(Long userId, String subtopicId) {
        logger.info("Starting isTutorialCompleted");

        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.CHECK_TUTORIAL_COMPLETED,
                    boolean.class,
                    userId,
                    subtopicId
            ));
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No tutorial completion record found for userId: {} and topicId: {}", userId, subtopicId);
            return false;
        }
    }

    @Override
    public double getAverageEloOfTopic(Long userId, String topicId) {
        logger.info("Starting getAverageElo");

        try {
            Double result = jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.CALCULATE_AVERAGE_ELO_OF_TOPIC,
                    Double.class,
                    userId,
                    topicId
            );

            return result != null ? result : 0.0;
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No subtopics found for userId: {} and topicId: {}", userId, topicId);
            return 0.0;
        }
    }

    @Override
    public double getAveragePKnowOfTopic(Long userId, String topicId) {
        logger.info("Starting getAveragePKnow");

        try {
            Double result = jdbcTemplate.queryForObject(
                    UserSubtopicMasteryConstant.CALCULATE_AVERAGE_P_KNOW_OF_TOPIC,
                    Double.class,
                    userId,
                    topicId
            );

            return result != null ? result : 0.0;
        }
        catch (EmptyResultDataAccessException e) {
            logger.warn("No subtopics found for userId: {} and topicId: {}", userId, topicId);
            return 0.0;
        }
    }


}
