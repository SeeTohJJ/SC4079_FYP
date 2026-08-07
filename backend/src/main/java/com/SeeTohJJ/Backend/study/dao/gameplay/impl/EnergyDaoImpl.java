package com.SeeTohJJ.Backend.study.dao.gameplay.impl;

import com.SeeTohJJ.Backend.study.constant.EnergyConstant;
import com.SeeTohJJ.Backend.study.dao.gameplay.EnergyDao;
import com.SeeTohJJ.Backend.study.model.gameplay.UserEnergy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class EnergyDaoImpl implements EnergyDao {
    private static final Logger logger = LoggerFactory.getLogger(EnergyDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EnergyDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserEnergy findByUserId(Long userId) {
        logger.info("Starting findByUserId");

        return jdbcTemplate.queryForObject(
                EnergyConstant.GET_USER_ENERGY_BY_USER_ID,
                (rs, rowNum) -> {
                    UserEnergy userEnergy = new UserEnergy();
                    userEnergy.setUserId(rs.getLong("user_id"));
                    userEnergy.setCurrentEnergy(rs.getInt("current_energy"));
                    userEnergy.setMaxEnergy(rs.getInt("max_energy"));
                    userEnergy.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
                    return userEnergy;
                },
                userId
        );
    }

    @Override
    public void updateEnergy(Long userId, int currentEnergy, LocalDateTime lastUpdated) {
        logger.info("Starting updateEnergy");

        jdbcTemplate.update(
                EnergyConstant.UPDATE_USER_ENERGY,
                currentEnergy,
                lastUpdated,
                userId
        );
    }
}
