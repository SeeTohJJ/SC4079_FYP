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

    private static final Logger logger = LoggerFactory.getLogger(TopicDaoImpl.class);

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

        logger.info("subtopicId: " + subtopicId + ", nodeType: " + nodeType + ", targetOrderIndex: " + targetOrderIndex);

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_NODE_ID_BY_ORDER_INDEX,
                String.class,
                subtopicId,
                targetOrderIndex,
                nodeType
        );
    }

    @Override
    public double getPInit(String subtopicId){
        logger.info("Starting getPInit");

        Double result = jdbcTemplate.queryForObject(
                TopicConstant.GET_P_INIT,
                Double.class,
                subtopicId
        );

        return result != null ? result : 0.0f;
    }

    @Override
    public String getTopicId(String nodeId){
        logger.info("Starting getTopicId");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_TOPIC_ID,
                String.class,
                nodeId
        );
    }

    @Override
    public BktParameters getBktParameters(String subtopicId){
        logger.info("Starting getBktParameters");

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_BKT_PARAMETERS,
                (rs, rowNum) -> {
                    BktParameters bkt = new BktParameters();
                    bkt.setSubTopic_id(subtopicId);
                    bkt.setP_init(rs.getDouble("p_init"));
                    bkt.setP_transit(rs.getDouble("p_transit"));
                    bkt.setP_slip(rs.getDouble("p_slip"));
                    bkt.setP_guess(rs.getDouble("p_guess"));
                    return bkt;
                },
                subtopicId
        );
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

}
