package com.SeeTohJJ.Backend.auth.dao.impl;

import com.SeeTohJJ.Backend.auth.controller.AuthController;
import com.SeeTohJJ.Backend.auth.dao.UserDao;
import com.SeeTohJJ.Backend.auth.mapper.UserRowMapper;
import com.SeeTohJJ.Backend.auth.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import  com.SeeTohJJ.Backend.auth.constant.AuthConstant;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserDaoImpl(DataSource dataSource, UserRowMapper userRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userRowMapper = userRowMapper;
    }

    @Override
    public boolean findEmailExist(String email) {

        Boolean exists = jdbcTemplate.queryForObject(
                AuthConstant.FIND_EMAIL_EXIST,
                Boolean.class,
                email
        );

        return exists != null && exists;
    }

    @Override
    public void registerUser(User user){
        jdbcTemplate.update(
                AuthConstant.INSERT_NEW_USER,
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.getPublicUserId(),
                user.getCreatedAt()
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                AuthConstant.FIND_USER_BY_EMAIL,
                userRowMapper,
                email
        );
    }
}
