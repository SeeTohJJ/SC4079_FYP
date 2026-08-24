package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.service.adaptive.ForgettingService;
import com.SeeTohJJ.Backend.user.service.mastery.UserSubtopicService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ForgettingServiceImpl implements ForgettingService {

    private static final Logger logger = LoggerFactory.getLogger(ForgettingServiceImpl.class);

    private final UserSubtopicService userSubtopicService;
    private final SubTopicService subTopicService;

    @Autowired
    public ForgettingServiceImpl(UserSubtopicService userSubtopicService, SubTopicService subTopicService) {
        this.userSubtopicService = userSubtopicService;
        this.subTopicService = subTopicService;
    }


    @Override
    public double applyForgetting(double currentPKnow, LocalDateTime lastUpdated) {
        logger.info("Start applyForgetting");

        long daysSinceReview = ChronoUnit.DAYS.between(lastUpdated, LocalDateTime.now());
        logger.info("daysSinceReview: " + daysSinceReview);
        double lambda = getForgettingRate(currentPKnow); // forgetting rate
        logger.info("lambda: " + lambda);
        logger.info("return: " + currentPKnow * Math.exp(-lambda * daysSinceReview));
        return currentPKnow * Math.exp(-lambda * daysSinceReview);
    }

    private double getForgettingRate(double pKnow) {
        if (pKnow >= 0.9) return 0.01;   // mastered concepts decay slowly
        if (pKnow >= 0.7) return 0.02;
        return 0.04;                     // weak concepts decay faster
    }

    @Override
    public void updateForgettingDecay(Long userId, String subtopicId) {
        logger.info("Start updateForgettingDecay");

        LocalDateTime lastUpdated = userSubtopicService.getLastUpdated(userId, subtopicId);
        userSubtopicService.setUserSubtopicPKnow(userId, subtopicId, applyForgetting(userSubtopicService.getUserPKnow(userId, subtopicId), lastUpdated));
    }

}
