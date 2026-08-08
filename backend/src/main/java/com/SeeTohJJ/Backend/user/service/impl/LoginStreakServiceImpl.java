package com.SeeTohJJ.Backend.user.service.impl;

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

    private final UserProfileDao userProfileDao;

    @Autowired
    public LoginStreakServiceImpl(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Override
    public void updateDailyLoginStreak(Long userId) {
        logger.info("Starting updateDailyLoginStreak");

        LocalDate today = LocalDate.now();
        LocalDate lastLogin = userProfileDao.getLastLoginDate(userId);

        int currentStreak = userProfileDao.getCurrentStreak(userId);
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

        userProfileDao.updateLoginStreak(userId, newStreak, today);
    }

    @Override
    public int getCurrentStreak(Long userId) {
        logger.info("Starting getCurrentStreak");

        return userProfileDao.getCurrentStreak(userId);
    }
}
