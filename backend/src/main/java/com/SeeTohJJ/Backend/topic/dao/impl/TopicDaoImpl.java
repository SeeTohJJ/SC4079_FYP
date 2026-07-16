package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.auth.dao.impl.UserDaoImpl;
import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TopicDaoImpl implements TopicDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public TopicDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String getSubTopicId(String nodeId){
        logger.info("Starting getSubTopicId");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_SUBTOPIC_ID,
                String.class,
                nodeId
        );
    }

    @Override
    public boolean existsByNodeIndex(String subtopicId, int targetNodeIndex, String nodeType) {
        logger.info("Starting existsByNodeIndex");

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                TopicConstant.CHECK_NODE_INDEX_EXISTS,
                boolean.class,
                subtopicId,
                targetNodeIndex,
                nodeType
        ));
    }

    @Override
    public String getNodeId(String subtopicId, String nodeType, int targetOrderIndex) {
        logger.info("Starting getNodeId");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_NODE_ID_BY_ORDER_INDEX,
                String.class,
                subtopicId,
                targetOrderIndex,
                nodeType
        );
    }

}
