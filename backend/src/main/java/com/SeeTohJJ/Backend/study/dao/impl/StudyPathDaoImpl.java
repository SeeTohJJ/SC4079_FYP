package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.StudyPathConstant;
import com.SeeTohJJ.Backend.study.dao.StudyPathDao;
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
public class StudyPathDaoImpl implements StudyPathDao {

    private static final Logger logger = LoggerFactory.getLogger(StudyPathDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final StudyNodeRowMapper studyNodeRowMapper;
    private final StudyPathRowMapper studyPathRowMapper;

    @Autowired
    public StudyPathDaoImpl(DataSource dataSource, StudyNodeRowMapper studyNodeRowMapper,
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
                StudyPathConstant.GET_TUTORIAL_NODES,
                studyNodeRowMapper,
                topicId
        );
    }

    @Override
    public List<UserNodeProgress> getExistingNodePath(Long userId){
        logger.info("Starting getExistingNodePath");

        return jdbcTemplate.query(
                StudyPathConstant.GET_EXISTING_NODE_PATH,
                studyPathRowMapper,
                userId
        );
    }

    @Override
    public boolean hasActiveNodes(Long userId) {
        logger.info("Starting hasActiveNodes");

        Integer count = jdbcTemplate.queryForObject(
                StudyPathConstant.COUNT_ACTIVE_NODES,
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
                StudyPathConstant.INSERT_NODE_INTO_USER_PROGRESS,
                userId,
                nodeId,
                nodeType,
                positionIndex,
                isUnlocked,
                isCompleted
        );

    }

    @Override
    public void completeNode(Long userId, String nodeId){
        logger.info("Starting completeNode");

        jdbcTemplate.update(
                StudyPathConstant.COMPLETE_NODE_WITH_USER_ID,
                userId,
                nodeId
        );
    }

    @Override
    public String getCorrectAnswer(String nodeId){
        logger.info("Starting getCorrectAnswer");

        return jdbcTemplate.queryForObject(
                StudyPathConstant.GET_CORRECT_ANSWER,
                (rs, rowNum) -> rs.getString("correct_answer"),
                nodeId
        );
    }

    @Override
    public int getNodePositionalIndex(Long userId, String nodeId){
        logger.info("Starting getNodePositionalIndex");

        return jdbcTemplate.queryForObject(
                StudyPathConstant.GET_NODE_POSITIONAL_INDEX,
                (rs, rowNum) -> rs.getInt("position_index"),
                userId,
                nodeId
        );
    }

    @Override
    public void unlockNextNode(Long userId, int nodePosIndex) {
        logger.info("Starting unlockNextNode");

        jdbcTemplate.update(
                StudyPathConstant.UNLOCK_NEXT_NODE,
                userId,
                nodePosIndex
        );
    }

    @Override
    public boolean checkIfNodeExistInProgress(Long userId, int nodePosIndex) {
        logger.info("Starting checkIfNodeExistInProgress");

        Integer count = jdbcTemplate.queryForObject(
                StudyPathConstant.CHECK_IF_NODE_POS_EXIST_IN_PROGRESS,
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
                StudyPathConstant.GET_CURRENT_SUBTOPIC,
                (rs, rowNum) -> rs.getString("subtopic_id"),
                userId
        );
    }

    @Override
    public int getUserLastPositionIndex(Long userId){
        logger.info("Starting getUserLastPositionIndex");

        try {
            Integer result = jdbcTemplate.queryForObject(
                    StudyPathConstant.GET_NODE_PATH_LAST_POS_INDEX,
                    (rs, rowNum) -> rs.getInt("position_index"),
                    userId
            );
            return result != null ? result : 1;
        } catch (EmptyResultDataAccessException e) {
            return 1;
        }
    }

    @Override
    public List<String> getIncorrectNodes(Long userId, String subtopicId, int reviewNodeCount){
        logger.info("Starting getIncorrectNodes");
        logger.info("Parameters: userID {}, {}, {}",  userId, subtopicId, reviewNodeCount);

        try {
            return jdbcTemplate.queryForList(
                    StudyPathConstant.GET_INCORRECT_NODES,
                    String.class,
                    userId,
                    subtopicId,
                    reviewNodeCount
            );
        } catch (Exception e) {
            logger.error("Error retrieving incorrect nodes: {}", e.getMessage());
            return null;
        }
    }



}
