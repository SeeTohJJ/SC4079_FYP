package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.auth.dao.impl.UserDaoImpl;
import com.SeeTohJJ.Backend.topic.constant.TopicConstant;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.dto.TopicDTO;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TopicDaoImpl implements TopicDao {

    private static final Logger logger = LoggerFactory.getLogger(TopicDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public TopicDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
    public String getTopicName(String topicId){
        logger.info("Starting getTopicName {}", topicId);

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_TOPIC_NAME,
                String.class,
                topicId
        );
    }

    @Override
    public List<TopicDTO> getAllTopics(){
        logger.info("Starting getAllTopics");

        return jdbcTemplate.query(
                TopicConstant.GET_ALL_TOPICS,
                (rs, rowNum) -> {
                    TopicDTO topicDTO = new TopicDTO();
                    topicDTO.setTopicId(rs.getString("topic_id"));
                    topicDTO.setTopicName(rs.getString("topic_name"));
                    topicDTO.setTopicDescription(rs.getString("description"));
                    topicDTO.setActive(rs.getBoolean("is_active"));
                    return topicDTO;
                }
        );
    }

    @Override
    public TopicDTO findById(String topicId){
        logger.info("Starting findById {}", topicId);

        return jdbcTemplate.queryForObject(
                TopicConstant.GET_TOPIC_BY_ID,
                (rs, rowNum) -> {
                    TopicDTO topicDTO = new TopicDTO();
                    topicDTO.setTopicId(rs.getString("topic_id"));
                    topicDTO.setTopicName(rs.getString("topic_name"));
                    topicDTO.setTopicDescription(rs.getString("description"));
                    topicDTO.setActive(rs.getBoolean("is_active"));
                    return topicDTO;
                },
                topicId
        );
    }

    @Override
    public void setTopicInactive(String topicId){
        logger.info("Starting setTopicInactive");

        jdbcTemplate.update(
                TopicConstant.SET_TOPIC_INACTIVE,
                topicId
        );
    }

    @Override
    public void setTopicActive(String topicId){
        logger.info("Starting setTopicActive");

        jdbcTemplate.update(
                TopicConstant.SET_TOPIC_ACTIVE,
                topicId
        );
    }

    @Override
    public boolean existsByName(String topicName){
        logger.info("Starting existsByName {}", topicName);

        Boolean exists = jdbcTemplate.queryForObject(
                TopicConstant.CHECK_TOPIC_EXIST_BY_NAME,
                Boolean.class,
                topicName
        );

        return Boolean.TRUE.equals(exists);
    }

    @Override
    public String findNextTopicId(){
        logger.info("Starting findNextTopicId");

        Integer maxId = jdbcTemplate.queryForObject(
                TopicConstant.FIND_NEXT_TOPIC_ID,
                Integer.class
        );

        int nextId = maxId + 1;
        return String.format("T%03d", nextId);

    }

    @Override
    public void create(String topicId, String topicName, String topicDescription){
        logger.info("Starting create");

        jdbcTemplate.update(
                TopicConstant.INSERT_TOPIC,
                topicId,
                topicName,
                topicDescription
        );
    }

    @Override
    public void update(String topicId, String topicName, String topicDescription){
        logger.info("Starting update");

        jdbcTemplate.update(
                TopicConstant.UPDATE_TOPIC,
                topicName,
                topicDescription,
                topicId
        );
    }

    @Override
    public int getActiveCount(){
        logger.info("Starting getActiveCount");

        Integer count = jdbcTemplate.queryForObject(
                TopicConstant.GET_ACTIVE_TOPIC_COUNT,
                Integer.class
        );

        return (count != null) ? count : 0;
    }
}
