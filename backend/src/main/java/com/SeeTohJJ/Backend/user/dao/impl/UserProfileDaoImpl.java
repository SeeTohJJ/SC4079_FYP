package com.SeeTohJJ.Backend.user.dao.impl;

import com.SeeTohJJ.Backend.user.constant.UserConstant;
import com.SeeTohJJ.Backend.user.dao.UserProfileDao;
import com.SeeTohJJ.Backend.user.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserProfileDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void setUserProfile(UserProfile userProfile){
        logger.info("Starting setUserProfile");

        jdbcTemplate.update(
                UserConstant.INSERT_USER_PROFILE,
                userProfile.getUserId(),
                userProfile.getUsername(),
                userProfile.getGender(),
                userProfile.getAge(),
                userProfile.getEmploymentStatus(),
                userProfile.getIncome(),
                userProfile.getCountry()
        );
    }

    @Override
    public Integer getCurrentStreak(Long userId) {
        logger.info("Starting getCurrentStreak");

        return jdbcTemplate.queryForObject(
                UserConstant.GET_CURRENT_LOGIN_STREAK,
                Integer.class,
                userId
        );
    }

    @Override
    public LocalDate getLastLoginDate(Long userId) {
        logger.info("Starting getLastLoginDate");

        try {
            return jdbcTemplate.queryForObject(
                    UserConstant.GET_LAST_LOGIN_DATE,
                    LocalDate.class,
                    userId
            );
        } catch (Exception e) {
            logger.error("Error retrieving last login date: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateLoginStreak(Long userId, int streak, LocalDate loginDate) {
        logger.info("Starting updateLoginStreak");

        jdbcTemplate.update(
                UserConstant.UPDATE_LOGIN_STREAK,
                streak,
                loginDate,
                userId
        );
    }
}
