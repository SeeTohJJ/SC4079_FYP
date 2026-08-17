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
    public int getActiveUserCount(){
        logger.info("Starting getActiveUserCount");

        Integer count = jdbcTemplate.queryForObject(
                UserConstant.GET_ACTIVE_USER_COUNT,
                Integer.class
        );

        return (count != null) ? count : 0;
    }

    @Override
    public int getActiveAdminCount(){
        logger.info("Starting getActiveAdminCount");

        Integer count = jdbcTemplate.queryForObject(
                UserConstant.GET_ACTIVE_ADMIN_COUNT,
                Integer.class
        );

        return (count != null) ? count : 0;
    }

}
