package com.SeeTohJJ.Backend.user.service.mastery.impl;

import com.SeeTohJJ.Backend.user.dao.mastery.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.user.service.mastery.UserSubtopicService;
import com.SeeTohJJ.Backend.user.service.mastery.UserTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserTopicServiceImpl implements UserTopicService {

    private static final Logger logger = LoggerFactory.getLogger(UserTopicServiceImpl.class);

    private final UserTopicMasteryDao userTopicMasteryDao;
    private final UserSubtopicService userSubtopicService;

    @Autowired
    public UserTopicServiceImpl(UserTopicMasteryDao userTopicMasteryDao,
                                UserSubtopicService userSubtopicService
    ) {
        this.userTopicMasteryDao = userTopicMasteryDao;
        this.userSubtopicService = userSubtopicService;
    }

    @Override
    public void calculateAverageElo(Long userId, String topicId){
        logger.info("Starting calculateAverageElo");

        userTopicMasteryDao.setAverageElo(userId, topicId, userSubtopicService.getAverageEloOfTopic(userId, topicId));
    }

    @Override
    public void calculateAveragePKnow(Long userId, String topicId){
        logger.info("Starting calculateAveragePKnow");

        userTopicMasteryDao.setAveragePKnow(userId, topicId, userSubtopicService.getAveragePKnowOfTopic(userId, topicId));
    }

    @Override
    public void updateTopicMasteryAverage(Long userId, String topicId){
        logger.info("Starting updateTopicMasteryAverage");

        calculateAverageElo(userId, topicId);
        calculateAveragePKnow(userId, topicId);
    }

    @Override
    public double getAverageElo(Long userId, String topicId){
        logger.info("Starting getAverageElo");

        return userTopicMasteryDao.getAverageElo(userId, topicId);
    }

    @Override
    public double getAveragePKnow(Long userId, String topicId){
        logger.info("Starting getAveragePKnow");

        return userTopicMasteryDao.getAveragePKnow(userId, topicId);
    }

    @Override
    public LocalDate getNextReviewDate(Long userId, String topicId){
        logger.info("Starting getNextReviewDate");

        return userTopicMasteryDao.getNextReview(userId, topicId);
    }


}
