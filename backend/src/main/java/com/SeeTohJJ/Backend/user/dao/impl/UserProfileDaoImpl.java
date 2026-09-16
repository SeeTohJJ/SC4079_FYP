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

    @Override
    public String getNameFromId(Long userId){
        logger.info("Starting getNameFromId");

        return jdbcTemplate.query(
                UserConstant.GET_NAME_FROM_ID,
                rs -> {
                    if (rs.next()) {
                        return rs.getString("username");
                    } else {
                        return null;
                    }
                },
                userId
        );
    }

    @Override
    public UserProfile getUserProfile(Long userId){
        logger.info("Starting getUserProfile");

        return jdbcTemplate.query(
                UserConstant.GET_USER_PROFILE,
                rs -> {
                    if (rs.next()) {
                        UserProfile userProfile = new UserProfile();
                        userProfile.setUserId(rs.getLong("user_id"));
                        userProfile.setUsername(rs.getString("username"));
                        userProfile.setGender(rs.getString("gender"));
                        userProfile.setAge(rs.getInt("age"));
                        userProfile.setEmploymentStatus(rs.getString("employment_status"));
                        userProfile.setIncome(rs.getInt("income"));
                        userProfile.setCountry(rs.getString("country"));
                        return userProfile;
                    } else {
                        return null;
                    }
                },
                userId
        );

    }

}
