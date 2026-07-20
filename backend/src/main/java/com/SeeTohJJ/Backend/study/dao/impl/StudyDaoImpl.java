package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.StudyConstant;
import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
import com.SeeTohJJ.Backend.study.mapper.StudyNodeRowMapper;
import com.SeeTohJJ.Backend.study.mapper.StudyPathRowMapper;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudyDaoImpl implements StudyDao {

    private static final Logger logger = LoggerFactory.getLogger(StudyDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final StudyNodeRowMapper studyNodeRowMapper;
    private final StudyPathRowMapper studyPathRowMapper;

    @Autowired
    public StudyDaoImpl(DataSource dataSource, StudyNodeRowMapper studyNodeRowMapper,
                        StudyPathRowMapper studyPathRowMapper)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.studyNodeRowMapper = studyNodeRowMapper;
        this.studyPathRowMapper = studyPathRowMapper;
    }


    @Override
    public List<StudyNode> getTutorialNodes(String topicId){
        logger.info("Starting getTutorialNodes");

        return jdbcTemplate.query(
                StudyConstant.GET_TUTORIAL_NODES,
                studyNodeRowMapper,
                topicId
        );
    }

    @Override
    public List<StudyNode> generateAdaptiveNodes(Long userId, String topicId){
        logger.info("Starting generateAdaptiveNodes");

        return jdbcTemplate.query(
                StudyConstant.GET_TUTORIAL_NODES,
                studyNodeRowMapper,
                topicId
        );
    }

    @Override
    public List<UserNodeProgress> getExistingNodePath(Long userId){
        logger.info("Starting getExistingNodePath");

        return jdbcTemplate.query(
                StudyConstant.GET_EXISTING_NODE_PATH,
                studyPathRowMapper,
                userId
        );
    }

    @Override
    public boolean hasActiveNodes(Long userId) {
        logger.info("Starting hasActiveNodes");

        Integer count = jdbcTemplate.queryForObject(
                StudyConstant.COUNT_ACTIVE_NODES,
                Integer.class,
                userId
        );

        return count != null && count > 0;
    }

    @Override
    public void insertNodeIntoUserProgress(Long userId,
                                           String nodeId,
                                           String nodeType,
                                           int positionIndex,
                                           boolean isUnlocked,
                                           boolean isCompleted){
        logger.info("Starting insertNodeIntoUserProgress");

        jdbcTemplate.update(
                StudyConstant.INSERT_NODE_INTO_USER_PROGRESS,
                userId,
                nodeId,
                nodeType,
                positionIndex,
                isUnlocked,
                isCompleted
        );

    }

    @Override
    public LessonNodeDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_LESSON_NODE_CONTENT,
                (rs, rowNum) -> {
                    LessonNodeDTO dto = new LessonNodeDTO();
                    dto.setNodeId(rs.getString("node_id"));
                    dto.setTitle(rs.getString("title"));
                    dto.setContent(rs.getString("content"));
                    return dto;
                },
                nodeId
        );
    }


    @Override
    public QuizNodeDTO getQuizNodeContent(String nodeId){
        logger.info("Starting getQuizNodeContent {}", nodeId);

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_QUIZ_NODE_CONTENT,
                (rs, rowNum) -> {
                    QuizNodeDTO dto = new QuizNodeDTO();
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
    }

    @Override
    public DecisionNodeDTO getDecisionNodeContent(String nodeId){
        logger.info("Starting getDecisionNodeContent");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_DECISION_NODE_CONTENT,
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
    }

    @Override
    public EventNodeDTO getEventNodeContent(String nodeId){
        logger.info("Starting getEventNodeContent");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_EVENT_NODE_CONTENT,
                (rs, rowNum) -> {
                    EventNodeDTO dto = new EventNodeDTO();
                    dto.setContent(rs.getString("content"));
                    dto.setResult(rs.getString("result"));
                    return dto;
                },
                nodeId
        );
    }

    @Override
    public void completeNode(Long userId, String nodeId){
        logger.info("Starting completeNode");

        jdbcTemplate.update(
                StudyConstant.COMPLETE_NODE_WITH_USER_ID,
                userId,
                nodeId
        );
    }

    @Override
    public String getCorrectAnswer(String nodeId){
        logger.info("Starting getCorrectAnswer");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_CORRECT_ANSWER,
                (rs, rowNum) -> rs.getString("correct_answer"),
                nodeId
        );
    }

    @Override
    public void saveUserQuestionAttempt(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken){
        logger.info("Starting saveUserQuestionAttempt");

        logger.info(userId  + " " + nodeId + " " + isCorrectAnswer + " " + timeTaken);

        jdbcTemplate.update(
                StudyConstant.SAVE_USER_QUESTION_ATTEMPT,
                userId,
                nodeId,
                isCorrectAnswer,
                timeTaken
        );
    }

    @Override
    public double getQuestionRating(String nodeId){
        logger.info("Starting getQuestionRating");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_QUESTION_RATING,
                (rs, rowNum) -> rs.getDouble("difficulty_rating"),
                nodeId
        );
    }

    @Override
    public int getNodePositionalIndex(Long userId, String nodeId){
        logger.info("Starting getNodePositionalIndex");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_NODE_POSITIONAL_INDEX,
                (rs, rowNum) -> rs.getInt("position_index"),
                userId,
                nodeId
        );
    }

    @Override
    public void unlockNextNode(Long userId, int nodePosIndex) {
        logger.info("Starting unlockNextNode");

        jdbcTemplate.update(
                StudyConstant.UNLOCK_NEXT_NODE,
                userId,
                nodePosIndex
        );
    }

    @Override
    public boolean checkIfNextNodeExist(Long userId, int nodePosIndex) {
        logger.info("Starting checkIfNextNodeExist");

        Integer count = jdbcTemplate.queryForObject(
                StudyConstant.CHECK_NEXT_NODE_EXIST,
                Integer.class,
                userId,
                nodePosIndex
        );

        return count != null && count > 0;
    }

    @Override
    public String getCurrentSubtopic(Long userId){
        logger.info("Starting getCurrentSubtopic");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_CURRENT_SUBTOPIC,
                (rs, rowNum) -> rs.getString("subtopic_id"),
                userId
        );
    }

    @Override
    public String getLowestPKnowSubtopic(Long userId){
        logger.info("Starting getLowestPKnowSubtopic");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_LOWEST_P_KNOW_SUBTOPIC,
                (rs, rowNum) -> rs.getString("subtopic_id"),
                userId
        );
    }

    @Override
    public String getLowestPKnowSubtopicNotMastered(Long userId){
        logger.info("Starting getLowestPKnowSubtopicNotMastered");

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_LOWEST_P_KNOW_SUBTOPIC_NOT_MASTERED,
                (rs, rowNum) -> rs.getString("subtopic_id"),
                userId
        );
    }

    @Override
    public int getCurrentChain(Long userId, String subtopicId){
        logger.info("Starting getCurrentChain");

        logger.info(userId+":"+subtopicId);

        return jdbcTemplate.queryForObject(
                StudyConstant.GET_USER_SUBTOPIC_CURRENT_CHAIN,
                (rs, rowNum) -> rs.getInt("current_chain"),
                userId,
                subtopicId
        );
    }

    @Override
    public int getUserLastPositionIndex(Long userId){
        logger.info("Starting getUserLastPositionIndex");

        try {
            Integer result = jdbcTemplate.queryForObject(
                    StudyConstant.GET_NODE_PATH_LAST_POS_INDEX,
                    (rs, rowNum) -> rs.getInt("current_chain"),
                    userId
            );
            return result != null ? result : 1;
        } catch (EmptyResultDataAccessException e) {
            return 1;
        }
    }


}
