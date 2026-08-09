package com.SeeTohJJ.Backend.garden.dao.impl;

import com.SeeTohJJ.Backend.garden.constant.GardenConstant;
import com.SeeTohJJ.Backend.garden.dao.UserPlantDao;
import com.SeeTohJJ.Backend.garden.model.UserPlant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserPlantDaoImpl implements UserPlantDao {

    private static final Logger logger = LoggerFactory.getLogger(UserPlantDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserPlantDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<UserPlant> findByUserId(Long userId) {
        logger.info("Starting findByUserId for userId: {}", userId);

        return jdbcTemplate.query(
                GardenConstant.GET_USER_PLANTS_BY_USER_ID,
                (rs, rowNum) -> {
                    UserPlant userPlant = new UserPlant();
                    userPlant.setUserId(rs.getLong("user_id"));
                    userPlant.setTopicId(rs.getString("topic_id"));
                    userPlant.setTopicName(rs.getString("topic_name"));
                    userPlant.setCurrentGrowth(rs.getDouble("current_growth"));
                    userPlant.setMaxGrowth(rs.getDouble("max_growth"));
                    userPlant.setHappiness(rs.getInt("happiness"));
                    java.sql.Timestamp lastWatered = rs.getTimestamp("last_watered");
                    if (lastWatered != null) {
                        userPlant.setLastWatered(lastWatered.toLocalDateTime());
                    }
                    java.sql.Timestamp lastGrowthUpdate = rs.getTimestamp("last_growth_update");
                    if (lastGrowthUpdate != null) {
                        userPlant.setLastGrowthUpdate(lastGrowthUpdate.toLocalDateTime());
                    }
                    userPlant.setStage(rs.getString("stage"));
                    userPlant.setPKnow(rs.getDouble("average_p_know"));
                    return userPlant;
                },
                userId
        );
    }

    @Override
    public UserPlant findByUserIdAndTopicId(Long userId, String topicId) {
        logger.info("Starting findByUserIdAndTopicId for userId: {}", userId);

        return jdbcTemplate.queryForObject(
                GardenConstant.GET_USER_PLANT_BY_USER_ID_AND_TOPIC_ID,
                (rs, rowNum) -> {
                    UserPlant userPlant = new UserPlant();
                    userPlant.setUserId(rs.getLong("user_id"));
                    userPlant.setTopicId(rs.getString("topic_id"));
                    userPlant.setTopicName(rs.getString("topic_name"));
                    userPlant.setCurrentGrowth(rs.getDouble("current_growth"));
                    userPlant.setMaxGrowth(rs.getDouble("max_growth"));
                    userPlant.setHappiness(rs.getInt("happiness"));
                    java.sql.Timestamp lastWatered = rs.getTimestamp("last_watered");
                    if (lastWatered != null) {
                        userPlant.setLastWatered(lastWatered.toLocalDateTime());
                    }
                    java.sql.Timestamp lastGrowthUpdate = rs.getTimestamp("last_growth_update");
                    if (lastGrowthUpdate != null) {
                        userPlant.setLastGrowthUpdate(lastGrowthUpdate.toLocalDateTime());
                    }
                    userPlant.setStage(rs.getString("stage"));
                    userPlant.setPKnow(rs.getDouble("average_p_know"));
                    return userPlant;
                },
                userId,
                topicId
        );
    }

    @Override
    public void update(UserPlant plant){
        logger.info("Starting update user plant: {}", plant);

        jdbcTemplate.update(
                GardenConstant.UPDATE_USER_PLANT,
                plant.getCurrentGrowth(),
                plant.getMaxGrowth(),
                plant.getHappiness(),
                plant.getLastWatered(),
                plant.getLastGrowthUpdate(),
                plant.getStage(),
                plant.getUserId(),
                plant.getTopicId()
        );
    }

    @Override
    public void insert(Long userId, String topicId) {
        logger.info("Starting insert user plant: {}", userId);

        jdbcTemplate.update(
                GardenConstant.INSERT_USER_PLANT,
                userId,
                topicId
        );
    }


}
