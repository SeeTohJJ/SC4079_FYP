package com.SeeTohJJ.Backend.topic.dao.impl;

import com.SeeTohJJ.Backend.auth.dao.impl.UserDaoImpl;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
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


}
