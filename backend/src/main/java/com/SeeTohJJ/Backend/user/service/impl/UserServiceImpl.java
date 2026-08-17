package com.SeeTohJJ.Backend.user.service.impl;

import com.SeeTohJJ.Backend.auth.dto.RegisterRequestDTO;
import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.garden.service.GardenService;
import com.SeeTohJJ.Backend.study.service.gameplay.EnergyService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import com.SeeTohJJ.Backend.user.dao.UserProfileDao;
import com.SeeTohJJ.Backend.user.model.UserProfile;
import com.SeeTohJJ.Backend.user.service.LoginStreakService;
import com.SeeTohJJ.Backend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserProfileDao userProfileDao;
    private final GardenService gardenService;
    private final EnergyService energyService;
    private final TopicService topicService;
    private final LoginStreakService loginStreakService;

    @Autowired
    public UserServiceImpl(UserProfileDao userProfileDao,
                           GardenService gardenService,
                           EnergyService energyService,
                           TopicService topicService,
                           LoginStreakService loginStreakService) {
        this.userProfileDao = userProfileDao;
        this.gardenService = gardenService;
        this.energyService = energyService;
        this.topicService = topicService;
        this.loginStreakService = loginStreakService;
    }

    @Override
    public void insertAllUserInitialTables(Long userId, RegisterRequestDTO request) {
        logger.info("Starting insertAllUserInitialTables");

        insertUserProfile(userId, request);
        insertInitialInterestTopicsTable(userId, request.getTopics());
        insertInitialLoginStreakTable(userId);
        insertInitialGardenTables(userId);
        insertInitialEnergyTables(userId);
    }

    private void insertUserProfile(Long userId, RegisterRequestDTO request) {
        logger.info("Starting setUserProfile");

        UserProfile userProfile = new UserProfile();

        userProfile.setUserId(userId);
        userProfile.setUsername(request.getUsername());
        userProfile.setGender(request.getGender());
        userProfile.setAge(request.getAge());
        userProfile.setEmploymentStatus(request.getEmploymentStatus());
        userProfile.setIncome(request.getIncome());
        userProfile.setCountry(request.getCountry());

        userProfileDao.setUserProfile(userProfile);
    }

    private void insertInitialGardenTables(Long userId) {
        logger.info("Starting insertInitialGardenTables");

        gardenService.insertInitialGardenTables(userId);
    }

    private void insertInitialEnergyTables(Long userId) {
        logger.info("Starting insertInitialEnergyTables");

        energyService.insertInitialEnergyTables(userId);
    }

    private void insertInitialInterestTopicsTable(Long userId, List<String> topics) {
        logger.info("Starting insertInitialInterestTopicsTable");

        for (String topicId : topics) {
            topicService.insertUserTopicInterest(userId, topicId);
        }
    }

    private void insertInitialLoginStreakTable(Long userId) {
        logger.info("Starting insertInitialLoginStreakTable");

        loginStreakService.insertInitialLoginStreak(userId);
    }

    @Override
    public int getActiveUserCount(){
        logger.info("Starting getActiveUserCount");

        return userProfileDao.getActiveUserCount();
    }

    @Override
    public int getActiveAdminCount(){
        logger.info("Starting getActiveAdminCount");

        return userProfileDao.getActiveAdminCount();
    }

}
