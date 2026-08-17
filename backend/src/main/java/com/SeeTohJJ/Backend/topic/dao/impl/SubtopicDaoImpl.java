package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.topic.constant.SubtopicConstant;
import com.SeeTohJJ.Backend.topic.dao.SubtopicDao;
import com.SeeTohJJ.Backend.topic.dto.SubtopicDTO;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SubtopicDaoImpl implements SubtopicDao {

    private static final Logger logger = LoggerFactory.getLogger(SubtopicDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public SubtopicDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public String getSubTopicId(String nodeId){
        logger.info("Starting getSubTopicId");

        return jdbcTemplate.queryForObject(
                SubtopicConstant.GET_SUBTOPIC_ID,
                String.class,
                nodeId
        );
    }

    @Override
    public boolean existsByNodeIndex(String subtopicId, int targetNodeIndex, String nodeType) {
        logger.info("Starting existsByNodeIndex");

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                SubtopicConstant.CHECK_NODE_INDEX_EXISTS,
                boolean.class,
                subtopicId,
                targetNodeIndex,
                nodeType
        ));
    }

    @Override
    public int getNodeDifficulty(String nodeId){
        logger.info("Starting getNodeDifficulty");

        Integer result = jdbcTemplate.queryForObject(
                SubtopicConstant.GET_NODE_DIFFICULTY,
                Integer.class,
                nodeId
        );
        return result != null ? result : 0;
    }

    @Override
    public String getNodeId(String subtopicId, String nodeType, int targetOrderIndex) {
        logger.info("Starting getNodeId");

        logger.info("subtopicId: " + subtopicId + ", nodeType: " + nodeType + ", targetOrderIndex: " + targetOrderIndex);

        return jdbcTemplate.queryForObject(
                SubtopicConstant.GET_NODE_ID_BY_ORDER_INDEX,
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
                SubtopicConstant.GET_P_INIT,
                Double.class,
                subtopicId
        );

        return result != null ? result : 0.0f;
    }

    @Override
    public BktParameters getBktParameters(String subtopicId){
        logger.info("Starting getBktParameters");

        return jdbcTemplate.queryForObject(
                SubtopicConstant.GET_BKT_PARAMETERS,
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
                SubtopicConstant.GET_TOPIC_ID_FROM_SUBTOPIC_ID,
                String.class,
                subtopicId
        );
    }

    @Override
    public boolean checkSubtopicExist(String subtopicId){
        logger.info("Starting checkSubtopicExist");

        Boolean result = jdbcTemplate.queryForObject(
                SubtopicConstant.CHECK_SUBTOPIC_EXIST,
                boolean.class,
                subtopicId
        );

        return Boolean.TRUE.equals(result);
    }

    @Override
    public List<SubtopicDTO> getAllSubtopics(){
        logger.info("Starting getAllSubtopics");

        return jdbcTemplate.query(
                SubtopicConstant.GET_ALL_SUBTOPICS,
                (rs, rowNum) -> {
                    SubtopicDTO dto = new SubtopicDTO();
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicName(rs.getString("name"));
                    dto.setDifficulty(rs.getInt("difficulty"));
                    dto.setPInit(rs.getDouble("p_init"));
                    dto.setPInit(rs.getObject("p_init", Double.class));
                    dto.setPTransit(rs.getObject("p_transit", Double.class));
                    dto.setPSlip(rs.getObject("p_slip", Double.class));
                    dto.setPGuess(rs.getObject("p_guess", Double.class));
                    dto.setActive(rs.getBoolean("is_active"));
                    return dto;
                }
        );
    }

    @Override
    public SubtopicDTO findById(String subtopicId){
        logger.info("Starting findById {}", subtopicId);

        return jdbcTemplate.queryForObject(
                SubtopicConstant.GET_SUBTOPIC_BY_ID,
                (rs, rowNum) -> {
                    SubtopicDTO dto = new SubtopicDTO();
                    dto.setSubtopicId(rs.getString("subtopic_id"));
                    dto.setTopicId(rs.getString("topic_id"));
                    dto.setSubtopicName(rs.getString("name"));
                    dto.setDifficulty(rs.getInt("difficulty"));
                    dto.setPInit(rs.getObject("p_init", Double.class));
                    dto.setPTransit(rs.getObject("p_transit", Double.class));
                    dto.setPSlip(rs.getObject("p_slip", Double.class));
                    dto.setPGuess(rs.getObject("p_guess", Double.class));
                    dto.setActive(rs.getBoolean("is_active"));
                    return dto;
                },
                subtopicId
        );
    }

    @Override
    public void setSubtopicInactive(String subtopicId){
        logger.info("Starting setSubtopicInactive");

        jdbcTemplate.update(
                SubtopicConstant.SET_SUBTOPIC_INACTIVE,
                subtopicId
        );
    }

    @Override
    public void setSubtopicActive(String subtopicId){
        logger.info("Starting setSubtopicActive");

        jdbcTemplate.update(
                SubtopicConstant.SET_SUBTOPIC_ACTIVE,
                subtopicId
        );
    }

    @Override
    public boolean existsByName(String subtopicName){
        logger.info("Starting existsByName {}", subtopicName);

        Boolean exists = jdbcTemplate.queryForObject(
                SubtopicConstant.CHECK_SUBTOPIC_EXIST_BY_NAME,
                Boolean.class,
                subtopicName
        );

        return Boolean.TRUE.equals(exists);
    }

    @Override
    public String findNextSubtopicId(String topicId){
        logger.info("Starting findNextSubtopicId");

        Integer maxId = jdbcTemplate.queryForObject(
                SubtopicConstant.FIND_NEXT_SUBTOPIC_ID,
                Integer.class,
                topicId
        );

        int nextId = (maxId != null) ? maxId + 1 : 1;
        return String.format("%sS%03d", topicId, nextId);
    }

    @Override
    public void create(String subtopicId,
                       String topicId,
                       String subtopicName,
                       int difficulty,
                       double pInit,
                       double pTransit,
                       double pSlip,
                       double pGuess){
        logger.info("Starting create");

        jdbcTemplate.update(
                SubtopicConstant.INSERT_SUBTOPIC,
                subtopicId,
                topicId,
                subtopicName,
                difficulty,
                pInit,
                pTransit,
                pSlip,
                pGuess
        );
    }

    @Override
    public void update(String subtopicId,
                       String topicId,
                       String subtopicName,
                       int difficulty,
                       double pInit,
                       double pTransit,
                       double pSlip,
                       double pGuess){
        logger.info("Starting update");

        jdbcTemplate.update(
                SubtopicConstant.UPDATE_SUBTOPIC,
                subtopicName,
                difficulty,
                pInit,
                pTransit,
                pSlip,
                pGuess,
                subtopicId
        );
    }

    @Override
    public int getActiveCount() {
        logger.info("Starting getActiveCount");

        Integer count = jdbcTemplate.queryForObject(
                SubtopicConstant.GET_ACTIVE_SUBTOPIC_COUNT,
                Integer.class
        );

        return (count != null) ? count : 0;
    }
}
