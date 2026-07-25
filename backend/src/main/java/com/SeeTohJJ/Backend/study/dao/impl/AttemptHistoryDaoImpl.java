package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.AttemptHistoryConstant;
import com.SeeTohJJ.Backend.study.constant.StudyConstant;
import com.SeeTohJJ.Backend.study.dao.AttemptHistoryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AttemptHistoryDaoImpl implements AttemptHistoryDao {

    private static final Logger logger = LoggerFactory.getLogger(AttemptHistoryDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AttemptHistoryDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveUserQuestionAttempt(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken,  boolean hintUsed) {
        logger.info("Starting saveUserQuestionAttempt");

        try {
            jdbcTemplate.update(
                    AttemptHistoryConstant.SAVE_USER_QUESTION_ATTEMPT,
                    userId,
                    nodeId,
                    isCorrectAnswer,
                    timeTaken,
                    hintUsed
            );
        } catch (Exception e) {
            logger.error("Error saving user question attempt: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public int getQuizAttemptHistoryCount(Long userId, String nodeId) {
        logger.info("Starting getQuizAttemptHistoryCount");

        try {
            Integer count = jdbcTemplate.queryForObject(
                    AttemptHistoryConstant.GET_QUIZ_ATTEMPT_HISTORY_COUNT,
                    Integer.class,
                    userId,
                    nodeId
            );

            return count != null ? count : 0;
        } catch (Exception e) {
            logger.error("Error retrieving quiz attempt history count: {}", e.getMessage());
            throw e;
        }
    }
}
