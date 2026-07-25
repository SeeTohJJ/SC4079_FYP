package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.NodeContentConstant;
import com.SeeTohJJ.Backend.study.dao.NodeContentDao;
import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
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
    public LessonNodeDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_LESSON_NODE_CONTENT,
                    (rs, rowNum) -> {
                        LessonNodeDTO dto = new LessonNodeDTO();
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
    public QuizNodeDTO getQuizNodeContent(String nodeId){
        logger.info("Starting getQuizNodeContent {}", nodeId);

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_QUIZ_NODE_CONTENT,
                    (rs, rowNum) -> {
                        QuizNodeDTO dto = new QuizNodeDTO();
                        dto.setNodeId(rs.getString("node_id"));
                        dto.setTitle(rs.getString("title"));
                        dto.setQuestion(rs.getString("content"));
                        dto.setOptionA(rs.getString("option_a"));
                        dto.setOptionB(rs.getString("option_b"));
                        dto.setOptionC(rs.getString("option_c"));
                        dto.setOptionD(rs.getString("option_d"));
                        dto.setHint(rs.getString("hint"));
                        dto.setExplanation(rs.getString("explanation"));
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
    public DecisionNodeDTO getDecisionNodeContent(String nodeId){
        logger.info("Starting getDecisionNodeContent");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_DECISION_NODE_CONTENT,
                    (rs, rowNum) -> {
                        DecisionNodeDTO dto = new DecisionNodeDTO();
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
    public EventNodeDTO getEventNodeContent(String nodeId){
        logger.info("Starting getEventNodeContent");

        try {
            return jdbcTemplate.queryForObject(
                    NodeContentConstant.GET_EVENT_NODE_CONTENT,
                    (rs, rowNum) -> {
                        EventNodeDTO dto = new EventNodeDTO();
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
}
