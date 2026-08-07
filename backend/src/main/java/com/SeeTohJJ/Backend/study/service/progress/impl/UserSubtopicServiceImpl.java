package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.study.dao.mastery.UserSubtopicMasteryDao;
import com.SeeTohJJ.Backend.study.service.progress.UserSubtopicService;
import com.SeeTohJJ.Backend.topic.model.UserSubTopicMastery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserSubtopicServiceImpl implements UserSubtopicService {

    private static final Logger logger = LoggerFactory.getLogger(UserSubtopicServiceImpl.class);

    private final UserSubtopicMasteryDao userSubtopicMasteryDao;

    @Autowired
    public UserSubtopicServiceImpl(UserSubtopicMasteryDao userSubtopicMasteryDao) {
        this.userSubtopicMasteryDao = userSubtopicMasteryDao;
    }

    @Override
    public double getUserPKnow(Long userId, String subTopicId) {
        logger.info("Starting getUserPKnow");

        return userSubtopicMasteryDao.getUserPKnow(userId, subTopicId);
    }

    @Override
    public void saveUserPKnow(Long userId, String subTopicId, double updatedPKnow) {
        logger.info("Starting saveUserPKnow");

        userSubtopicMasteryDao.saveUserPKnow(userId, subTopicId, updatedPKnow);
    }

    @Override
    public double getUserElo(Long userId, String subtopicId){
        logger.info("Starting getUserElo");

        return userSubtopicMasteryDao.getUserElo(userId, subtopicId);
    }

    @Override
    public void setUserElo(Long userId, String subtopicId, double newElo){
        logger.info("Starting setUserElo");

        userSubtopicMasteryDao.setUserElo(userId, subtopicId, newElo);
    }

    @Override
    public void setSubtopicIsMasteredToTrue(Long userId, String subtopicId){
        logger.info("Starting setSubtopicIsMasteredToTrue");

        userSubtopicMasteryDao.setSubtopicIsMasteredToTrue(userId, subtopicId);
    }

    @Override
    public void setSubtopicIsMasteredToFalse(Long userId, String subtopicId){
        logger.info("Starting setSubtopicIsMasteredToFalse");

        userSubtopicMasteryDao.setSubtopicIsMasteredToFalse(userId, subtopicId);
    }

    @Override
    public boolean isSubtopicMastered(Long userId, String subtopicId){
        logger.info("Starting isSubtopicMastered");

        return userSubtopicMasteryDao.isSubtopicMastered(userId, subtopicId);
    }

    @Override
    public void insertNewSubtopicMastery(Long userId, String subtopicId, double p_know){
        logger.info("Starting insertNewSubtopicMastery");

        userSubtopicMasteryDao.insertNewSubtopicMastery(userId, subtopicId, p_know);
    }

    @Override
    public boolean pKnowGreaterThanRating(Long userId, String subtopicId) {
        logger.info("Starting pKnowGreaterThanRating");

        double userPKnow = getUserPKnow(userId, subtopicId);
        double p_mastered = 0.8;

        return userPKnow > p_mastered;
    }

    @Override
    public void setUserSubTopicInterest(Long userId, String subtopicId){
        logger.info("Starting setUserSubTopicInterest");

//        userSubtopicMasteryDao.insertUserInterestedSubtopic(userId, subtopicId);
//        userSubtopicMasteryDao.insertInitialSubtopicProgress(userId, subtopicId);
    }

    @Override
    public void updateAttemptStatistics(Long userId, String subTopicId, boolean isCorrectAnswer) {
        logger.info("Starting updateAttemptStatistics");

        UserSubTopicMastery userAttemptHistory = userSubtopicMasteryDao.getUserAttemptHistory(userId, subTopicId);

        if (isCorrectAnswer) {
            userSubtopicMasteryDao.updateCorrectAttempt(
                    userId,
                    subTopicId,
                    userAttemptHistory.getAttemptCount() + 1 ,
                    userAttemptHistory.getCorrectCount() + 1
            );
        }
        else {
            userSubtopicMasteryDao.updateWrongAttempt(
                    userId,
                    subTopicId,
                    userAttemptHistory.getAttemptCount() + 1,
                    userAttemptHistory.getWrongCount() + 1
            );
        }
    }

    @Override
    public String getUserLowestPKnowSubtopic(Long userId){
        logger.info("Starting getUserLowestPKnowSubtopic");

        return userSubtopicMasteryDao.getLowestPKnowSubtopic(userId);
    }

    @Override
    public String getUserLowestPKnowSubtopicNotMastered(Long userId){
        logger.info("Starting getUserLowestPKnowSubtopicNotMastered");

        return userSubtopicMasteryDao.getLowestPKnowSubtopicNotMastered(userId);
    }

    @Override
    public int getCurrentChain(Long userId, String subtopicId){
        logger.info("Starting getCurrentChain");

        return userSubtopicMasteryDao.getCurrentChain(userId, subtopicId);
    }

    @Override
    public LocalDateTime getLastUpdated(Long userId, String subtopicId){
        logger.info("Starting getLastUpdated");

        return userSubtopicMasteryDao.getLastUpdated(userId, subtopicId);
    }

    @Override
    public void setUserSubtopicPKnow(Long userId, String subtopicId, double PKnow){
        logger.info("Starting setUserSubtopicPKnow");

        userSubtopicMasteryDao.setUserSubtopicPKnow(userId, subtopicId, PKnow);
    }

    @Override
    public void incrementHintUsage(Long userId, String subtopicId){
        logger.info("Starting incrementHintUsage");

        userSubtopicMasteryDao.incrementHintUsage(userId, subtopicId);
    }

    @Override
    public boolean isTutorialCompleted(Long userId, String topicId){
        logger.info("Starting isTutorialCompleted");

        return userSubtopicMasteryDao.isTutorialCompleted(userId, topicId);
    }

    @Override
    public double getAverageEloOfTopic(Long userId, String topicId){
        logger.info("Starting getAverageEloOfTopic");

        return userSubtopicMasteryDao.getAverageEloOfTopic(userId, topicId);
    }

    @Override
    public double getAveragePKnowOfTopic(Long userId, String topicId){
        logger.info("Starting getAveragePKnowOfTopic");

        return userSubtopicMasteryDao.getAveragePKnowOfTopic(userId, topicId);
    }

}
