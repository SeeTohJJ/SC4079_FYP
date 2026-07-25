package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.NodeContentConstant;
import com.SeeTohJJ.Backend.study.dao.NodeContentDao;
import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class NodeContentDaoImpl implements NodeContentDao {

    private static final Logger logger = LoggerFactory.getLogger(NodeContentDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NodeContentDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public LessonContentDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_LESSON_NODE_CONTENT,
                    (rs, rowNum) -> {
                        LessonContentDTO dto = new LessonContentDTO();
                        dto.setNodeId(rs.getString("node_id"));
                        dto.setTitle(rs.getString("title"));
                        dto.setContent(rs.getString("content"));
                        return dto;
                    },
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No lesson node found for: {}", nodeId);
            return null;
        }
    }


    @Override
    public QuizContentDTO getQuizNodeContent(String nodeId){
        logger.info("Starting getQuizNodeContent {}", nodeId);

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_QUIZ_NODE_CONTENT,
                    (rs, rowNum) -> {
                        QuizContentDTO dto = new QuizContentDTO();
                        dto.setNodeId(rs.getString("node_id"));
                        dto.setTitle(rs.getString("title"));
                        dto.setQuestion(rs.getString("content"));
                        dto.setOptionA(rs.getString("option_a"));
                        dto.setOptionB(rs.getString("option_b"));
                        dto.setOptionC(rs.getString("option_c"));
                        dto.setOptionD(rs.getString("option_d"));
                        return dto;
                    },
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No quiz node found for: {}", nodeId);
            return null;
        }
    }

    @Override
    public DecisionContentDTO getDecisionNodeContent(String nodeId){
        logger.info("Starting getDecisionNodeContent");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_DECISION_NODE_CONTENT,
                    (rs, rowNum) -> {
                        DecisionContentDTO dto = new DecisionContentDTO();
                        dto.setNodeId(rs.getString("node_id"));
                        dto.setTitle(rs.getString("title"));
                        dto.setContent(rs.getString("content"));
                        dto.setChoice_A(rs.getString("choice_a"));
                        dto.setChoice_B(rs.getString("choice_b"));
                        dto.setResult_A(rs.getString("result_a"));
                        dto.setResult_B(rs.getString("result_b"));
                        return dto;
                    },
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No decision node found for: {}", nodeId);
            return null;
        }
    }

    @Override
    public EventContentDTO getEventNodeContent(String nodeId){
        logger.info("Starting getEventNodeContent");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_EVENT_NODE_CONTENT,
                    (rs, rowNum) -> {
                        EventContentDTO dto = new EventContentDTO();
                        dto.setContent(rs.getString("content"));
                        dto.setResult(rs.getString("result"));
                        return dto;
                    },
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No event node found for: {}", nodeId);
            return null;
        }
    }

    @Override
    public double getQuestionRating(String nodeId){
        logger.info("Starting getQuestionRating");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_QUESTION_RATING,
                    (rs, rowNum) -> rs.getDouble("difficulty_rating"),
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No question rating found for: {}", nodeId);
            return 0.0;
        }
    }

    @Override
    public String getQuizHint(String nodeId){
        logger.info("Starting getQuizHint");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_QUIZ_HINT,
                    (rs, rowNum) -> rs.getString("hint"),
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No quiz hint found for: {}", nodeId);
            return null;
        }
    }

    @Override
    public String getQuizExplanation(String nodeId){
        logger.info("Starting getQuizExplanation");

        try{
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_QUIZ_EXPLANATION,
                    (rs, rowNum) -> rs.getString("explanation"),
                    nodeId
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("No quiz explanation found for: {}", nodeId);
            return null;
        }
    }
}
