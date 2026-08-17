package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.LessonRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.LessonResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.QuizResponseDTO;
import com.SeeTohJJ.Backend.study.constant.NodeContentConstant;
import com.SeeTohJJ.Backend.study.dao.NodeContentDao;
import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;
import com.SeeTohJJ.Backend.topic.constant.SubtopicConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

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
                        dto.setHint(rs.getString("hint"));
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

    @Override
    public List<LessonResponseDTO> getAllActiveLessons(){
        logger.info("Starting getAllActiveLessons");

        return jdbcTemplate.query(
                NodeContentConstant.GET_ALL_ACTIVE_LESSONS,
                (rs, rowNum) -> {
                    LessonResponseDTO dto = new LessonResponseDTO();
                    dto.setNodeId(rs.getString("node_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setOrderIndex(rs.getInt("order_index"));
                    dto.setRequiredMastery(rs.getInt("required_mastery"));
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setContent(rs.getString("content"));
                    return dto;
                }
        );
    }

    @Override
    public List<LessonResponseDTO> getAllInactiveLessons(){
        logger.info("Starting getAllInactiveLessons");

        return jdbcTemplate.query(
                NodeContentConstant.GET_ALL_INACTIVE_LESSONS,
                (rs, rowNum) -> {
                    LessonResponseDTO dto = new LessonResponseDTO();
                    dto.setNodeId(rs.getString("node_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setOrderIndex(rs.getInt("order_index"));
                    dto.setRequiredMastery(rs.getInt("required_mastery"));
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setContent(rs.getString("content"));
                    return dto;
                }
        );
    }

    @Override
    public LessonResponseDTO getLesson(String nodeId){
        logger.info("Starting getLesson");

        return jdbcTemplate.queryForObject(
                NodeContentConstant.GET_LESSON,
                (rs, rowNum) -> {
                    LessonResponseDTO dto = new LessonResponseDTO();
                    dto.setNodeId(rs.getString("node_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setOrderIndex(rs.getInt("order_index"));
                    dto.setRequiredMastery(rs.getInt("required_mastery"));
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setContent(rs.getString("content"));
                    return dto;
                },
                nodeId
        );
    }

    @Override
    public void createLesson(String nodeId,
                                          String topicId,
                                          String subtopicId,
                                          String title,
                                          int orderIndex,
                                          int requiredMastery,
                                          String content){
        logger.info("Starting createLesson");

        jdbcTemplate.update(
                NodeContentConstant.INSERT_LESSON_NODE,
                nodeId,
                topicId,
                subtopicId,
                title,
                orderIndex,
                requiredMastery
        );

        jdbcTemplate.update(
                NodeContentConstant.INSERT_LESSON_CONTENT,
                nodeId,
                title,
                content
        );
    }

    @Override
    public void updateLesson(String nodeId,
                             String topicId,
                             String subtopicId,
                             String title,
                             int orderIndex,
                             int requiredMastery,
                             String content){
        logger.info("Starting updateLesson");

        jdbcTemplate.update(
                NodeContentConstant.UPDATE_LESSON_NODE,
                topicId,
                subtopicId,
                title,
                orderIndex,
                requiredMastery,
                nodeId
        );

        jdbcTemplate.update(
                NodeContentConstant.UPDATE_LESSON_CONTENT,
                title,
                content,
                nodeId
        );
    }

    @Override
    public void setLessonInactive(String nodeId){
        logger.info("Starting setLessonInactive");

        jdbcTemplate.update(
                NodeContentConstant.SET_LESSON_INACTIVE,
                nodeId
        );
    }

    @Override
    public void setLessonActive(String nodeId){
        logger.info("Starting setLessonActive");

        jdbcTemplate.update(
                NodeContentConstant.SET_LESSON_ACTIVE,
                nodeId
        );
    }


    @Override
    public List<QuizResponseDTO> getAllQuizzes(){
        logger.info("Starting getAllQuizzes");

        return jdbcTemplate.query(
                NodeContentConstant.GET_ALL_QUIZZES,
                (rs, rowNum) -> {
                    QuizResponseDTO dto = new QuizResponseDTO();
                    dto.setNodeId(rs.getString("node_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setOrderIndex(rs.getInt("order_index"));
                    dto.setRequiredMastery(rs.getInt("required_mastery"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("last_updated");
                    if (timestamp != null) {
                        dto.setLastUpdated(timestamp.toLocalDateTime());
                    }
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setContent(rs.getString("content"));
                    dto.setOptionA(rs.getString("option_a"));
                    dto.setOptionB(rs.getString("option_b"));
                    dto.setOptionC(rs.getString("option_c"));
                    dto.setOptionD(rs.getString("option_d"));
                    dto.setCorrectAnswer(rs.getString("correct_answer"));
                    dto.setDifficultyRating(rs.getInt("difficulty_rating"));
                    dto.setHint(rs.getString("hint"));
                    dto.setExplanation(rs.getString("explanation"));
                    return dto;
                }
        );
    }

    @Override
    public QuizResponseDTO getQuiz(String nodeId){
        logger.info("Starting getQuiz");

        return jdbcTemplate.queryForObject(
                NodeContentConstant.GET_QUIZ,
                (rs, rowNum) -> {
                    QuizResponseDTO dto = new QuizResponseDTO();
                    dto.setNodeId(rs.getString("node_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setOrderIndex(rs.getInt("order_index"));
                    dto.setRequiredMastery(rs.getInt("required_mastery"));
                    java.sql.Timestamp timestamp = rs.getTimestamp("last_updated");
                    if (timestamp != null) {
                        dto.setLastUpdated(timestamp.toLocalDateTime());
                    }
                    dto.setActive(rs.getBoolean("is_active"));
                    dto.setContent(rs.getString("content"));
                    dto.setOptionA(rs.getString("option_a"));
                    dto.setOptionB(rs.getString("option_b"));
                    dto.setOptionC(rs.getString("option_c"));
                    dto.setOptionD(rs.getString("option_d"));
                    dto.setCorrectAnswer(rs.getString("correct_answer"));
                    dto.setDifficultyRating(rs.getInt("difficulty_rating"));
                    dto.setHint(rs.getString("hint"));
                    dto.setExplanation(rs.getString("explanation"));
                    return dto;
                },
                nodeId
        );
    }

    @Override
    public void createQuiz(String nodeId,
                                      String topicId,
                                      String subtopicId,
                                      String title,
                                      int orderIndex,
                                      int requiredMastery,
                                      String content,
                                      String optionA,
                                      String optionB,
                                      String optionC,
                                      String optionD,
                                      String correctAnswer,
                                      int difficultyRating,
                                      String hint,
                                      String explanation){
        logger.info("Starting createQuiz");

        jdbcTemplate.update(
                NodeContentConstant.INSERT_QUIZ_NODE,
                nodeId,
                topicId,
                subtopicId,
                title,
                orderIndex,
                requiredMastery
        );

        jdbcTemplate.update(
                NodeContentConstant.INSERT_QUIZ_CONTENT,
                nodeId,
                title,
                content,
                optionA,
                optionB,
                optionC,
                optionD,
                correctAnswer,
                difficultyRating,
                hint,
                explanation
        );

    }

    @Override
    public void updateQuiz(String nodeId,
                           String topicId,
                           String subtopicId,
                           String title,
                           int orderIndex,
                           int requiredMastery,
                           String content,
                           String optionA,
                           String optionB,
                           String optionC,
                           String optionD,
                           String correctAnswer,
                           int difficultyRating,
                           String hint,
                           String explanation){
        logger.info("Starting updateQuiz");

        jdbcTemplate.update(
                NodeContentConstant.UPDATE_QUIZ_NODE,
                topicId,
                subtopicId,
                title,
                orderIndex,
                requiredMastery,
                nodeId
        );

        jdbcTemplate.update(
                NodeContentConstant.UPDATE_QUIZ_CONTENT,
                title,
                content,
                optionA,
                optionB,
                optionC,
                optionD,
                correctAnswer,
                difficultyRating,
                hint,
                explanation,
                nodeId
        );

    }

    @Override
    public void setQuizInactive(String nodeId){
        logger.info("Starting setQuizInactive");

        jdbcTemplate.update(
                NodeContentConstant.SET_QUIZ_INACTIVE,
                nodeId
        );
    }

    @Override
    public void setQuizActive(String nodeId){
        logger.info("Starting setQuizActive");

        jdbcTemplate.update(
                NodeContentConstant.SET_QUIZ_ACTIVE,
                nodeId
        );
    }

    @Override
    public String findNextNodeId(String subtopicId, String nodeType){
        logger.info("Starting findNextNodeId");

        Integer maxId = jdbcTemplate.queryForObject(
                NodeContentConstant.FIND_NEXT_NODE_ID,
                Integer.class,
                subtopicId,
                nodeType
        );

        int nextId = (maxId != null) ? maxId + 1 : 1;
        String topicPart = subtopicId.substring(0, 4);
        String subtopicPart = subtopicId.substring(4);
        String nodeAcronym = nodeType.substring(0, 1).toUpperCase();

        return String.format("N-%s-%s-%s-%03d", topicPart, subtopicPart, nodeAcronym, nextId);
    }
}
