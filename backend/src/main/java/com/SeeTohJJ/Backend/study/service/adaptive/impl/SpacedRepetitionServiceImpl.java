package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.dao.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.study.service.adaptive.SpacedRepetitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SpacedRepetitionServiceImpl implements SpacedRepetitionService {

    private static final Logger logger = LoggerFactory.getLogger(SpacedRepetitionServiceImpl.class);

    private final UserTopicMasteryDao userTopicMasteryDao;

    @Autowired
    public SpacedRepetitionServiceImpl(UserTopicMasteryDao userTopicMasteryDao) {
        this.userTopicMasteryDao = userTopicMasteryDao;
    }

    @Override
    public void scheduleNextReview(Long userId, String topicId, double pKnow){
        logger.info("Starting scheduleNextReview");

        int interval = calculateReviewInterval(pKnow);

        LocalDate nextReview = LocalDate.now().plusDays(interval);
        userTopicMasteryDao.updateNextReviewDate(userId, topicId, nextReview);
        userTopicMasteryDao.updateIntervalDay(userId, topicId, interval);
    }

    private int calculateReviewInterval(double pKnow){
        logger.info("Starting calculateReviewInterval");

        int interval;

        if (pKnow < 0.4)
            interval = 1;
        else if (pKnow < 0.6)
            interval = 3;
        else if (pKnow < 0.8)
            interval = 7;
        else
            interval = 14;

        return interval;
    }

    @Override
    public boolean isReviewDue(Long userId, String topicId) {
        logger.info("Starting isReviewDue");

        LocalDate nextReview = userTopicMasteryDao.getNextReview(userId, topicId);
        return nextReview != null && !nextReview.isAfter(LocalDate.now());
    }

    @Override
    public List<String> getDueReviews(Long userId){
        logger.info("Starting getDueReviews");

        return userTopicMasteryDao.getDueReviews(userId);
    }




}
