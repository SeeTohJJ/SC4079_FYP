package com.SeeTohJJ.Backend.study.dao.impl;

import com.SeeTohJJ.Backend.study.constant.StudyConstant;
import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.StudyNodeDTO;
import com.SeeTohJJ.Backend.study.mapper.StudyNodeRowMapper;
import com.SeeTohJJ.Backend.study.mapper.StudyPathRowMapper;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
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
                StudyConstant.FIND_TUTORIAL_NODES,
                studyNodeRowMapper,
                topicId
        );
    }

    @Override
    public List<StudyNode> generateAdaptiveNodes(Long userId, String topicId){
        logger.info("Starting generateAdaptiveNodes");

        return jdbcTemplate.query(
                StudyConstant.FIND_TUTORIAL_NODES,
                studyNodeRowMapper,
                topicId
        );
    }

    @Override
    public List<UserNodeProgress> getExistingNodePath(Long userId){
        logger.info("Starting getExistingNodePath");

        return jdbcTemplate.query(
                StudyConstant.FIND_EXISTING_NODE_PATH,
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
                isCompleted,
                LocalDateTime.now()
        );

    }

    @Override
    public LessonNodeDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        return jdbcTemplate.queryForObject(
                StudyConstant.FIND_LESSON_NODE_CONTENT,
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

}
