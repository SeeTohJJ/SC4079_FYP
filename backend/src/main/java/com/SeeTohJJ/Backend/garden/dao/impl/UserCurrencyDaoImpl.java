package com.SeeTohJJ.Backend.garden.dao.impl;

import com.SeeTohJJ.Backend.garden.constant.GardenConstant;
import com.SeeTohJJ.Backend.garden.dao.UserCurrencyDao;
import com.SeeTohJJ.Backend.garden.model.UserCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserCurrencyDaoImpl implements UserCurrencyDao {
    private static final Logger logger = LoggerFactory.getLogger(UserCurrencyDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserCurrencyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserCurrency getCurrency(Long userId) {
        logger.info("Starting getCurrency for userId: {}", userId);

        try {
            return jdbcTemplate.queryForObject(
                    GardenConstant.GET_USER_CURRENCY_BY_USER_ID,
                    (rs, rowNum) -> {
                        UserCurrency userCurrency = new UserCurrency();
                        userCurrency.setUserId(rs.getLong("user_id"));
                        userCurrency.setCoins(rs.getInt("coins"));
                        userCurrency.setWater(rs.getInt("water"));
                        return userCurrency;
                    },
                    userId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(UserCurrency currency){
        logger.info("Starting updateCurrency for currency: {}", currency);

        jdbcTemplate.update(
                GardenConstant.UPDATE_USER_CURRENCY,
                currency.getCoins(),
                currency.getWater(),
                currency.getUserId()
        );
    }

    @Override
    public void insert(Long userId){
        logger.info("Starting insertCurrency for userId: {}", userId);

        jdbcTemplate.update(
                GardenConstant.INSERT_USER_CURRENCY,
                userId
        );
    }
}
