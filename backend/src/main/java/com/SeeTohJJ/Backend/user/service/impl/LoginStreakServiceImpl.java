package com.SeeTohJJ.Backend.user.service.impl;

import com.SeeTohJJ.Backend.user.dao.LoginStreakDao;
import com.SeeTohJJ.Backend.user.dao.UserProfileDao;
import com.SeeTohJJ.Backend.user.service.LoginStreakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoginStreakServiceImpl implements LoginStreakService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final LoginStreakDao loginStreakDao;

    @Autowired
    public LoginStreakServiceImpl(LoginStreakDao loginStreakDao) {
        this.loginStreakDao = loginStreakDao;
    }

    @Override
    public void updateDailyLoginStreak(Long userId) {
        logger.info("Starting updateDailyLoginStreak");

        LocalDate today = LocalDate.now();
        LocalDate lastLogin = loginStreakDao.getLastLoginDate(userId);

        int currentStreak = loginStreakDao.getCurrentStreak(userId);
        int newStreak;

        // Brand new account
        if (lastLogin == null) {
            newStreak = 1;
        }
        // Login today
        else if (lastLogin.equals(today)) {
            return;
        }
        // Login yesterday
        else if (lastLogin.equals(today.minusDays(1))) {
            newStreak = currentStreak + 1;
        }
        else {
            newStreak = 1;
        }

        loginStreakDao.updateLoginStreak(userId, newStreak, today);
    }

    @Override
    public int getCurrentStreak(Long userId) {
        logger.info("Starting getCurrentStreak");

        return loginStreakDao.getCurrentStreak(userId);
    }

    @Override
    public void insertInitialLoginStreak(Long userId) {
        logger.info("Starting insertInitialLoginStreak");

        loginStreakDao.insert(userId);
    }

}
