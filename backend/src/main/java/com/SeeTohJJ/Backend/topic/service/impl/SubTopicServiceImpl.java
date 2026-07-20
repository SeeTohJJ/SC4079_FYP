package com.SeeTohJJ.Backend.topic.service.impl;

import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import com.SeeTohJJ.Backend.topic.model.UserSubTopicMastery;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTopicServiceImpl implements SubTopicService {

    private static final Logger logger = LoggerFactory.getLogger(SubTopicServiceImpl.class);

    private final TopicDao topicDao;
    private final UserTopicMasteryDao userTopicMasteryDao;
    private final UserTopicDao userTopicDao;
    private final ProgressService progressService;

    @Autowired
    public SubTopicServiceImpl(TopicDao topicDao,
                               UserTopicMasteryDao userTopicMasteryDao,
                               UserTopicDao userTopicDao,
                               ProgressService progressService) {
        this.topicDao = topicDao;
        this.userTopicMasteryDao = userTopicMasteryDao;
        this.userTopicDao = userTopicDao;
        this.progressService = progressService;
    }

    @Override
    public String getSubTopicId(String nodeId) {
        logger.info("Starting getSubTopicId");

        return topicDao.getSubTopicId(nodeId);
    }

    @Override
    public BktParameters getBktParameters(String subtopicId) {
        logger.info("Starting getBktParameters");

        return userTopicMasteryDao.getBktParameters(subtopicId);
    }

    @Override
    public double getUserPKnow(Long userId, String subTopicId) {
        logger.info("Starting getUserPKnow");

        return userTopicMasteryDao.getUserPKnow(userId, subTopicId);
    }

    @Override
    public void updatePKnow(Long userId, String subTopicId, double updatedPKnow) {
        logger.info("Starting updatePKnow");

        userTopicMasteryDao.updatePKnow(userId, subTopicId, updatedPKnow);
    }

    @Override
    public void updateAttemptStatistics(Long userId, String subTopicId, boolean isCorrectAnswer) {
        logger.info("Starting updateAttemptStatistics");

        UserSubTopicMastery userAttemptHistory = userTopicMasteryDao.getUserAttemptHistory(userId, subTopicId);

        if (isCorrectAnswer) {
            userTopicMasteryDao.updateCorrectAttempt(
                    userId,
                    subTopicId,
                    userAttemptHistory.getAttemptCount() + 1 ,
                    userAttemptHistory.getCorrectCount() + 1
            );
        }
        else {
            userTopicMasteryDao.updateWrongAttempt(
                    userId,
                    subTopicId,
                    userAttemptHistory.getAttemptCount() + 1,
                    userAttemptHistory.getWrongCount() + 1
            );
        }
    }

    @Override
    public double getUserElo(Long userId, String subtopicId){
        logger.info("Starting getUserElo");

        return userTopicMasteryDao.getUserElo(userId, subtopicId);
    }

    @Override
    public void setUserNewEloRating(Long userId, String subtopicId, double newElo){
        logger.info("Starting setUserNewEloRating");

        userTopicMasteryDao.setUserNewEloRating(userId, subtopicId, newElo);
    }

    @Override
    public boolean pKnowGreaterThanRating(Long userId, String subtopicId) {
        logger.info("Starting pKnowGreaterThanRating");

        double userPKnow = userTopicMasteryDao.getUserPKnow(userId, subtopicId);

        return userPKnow > 0.9;
    }

    @Override
    public void updateSubTopicMastery(Long userId, String subtopicId){
        logger.info("Starting updateSubTopicMastery");

        userTopicMasteryDao.setIsMasteredToTrue(userId, subtopicId);
    }

    @Override
    public boolean isSubtopicMastered(Long userId, String subtopicId){
        logger.info("Starting isSubtopicMastered");

        return userTopicMasteryDao.isSubtopicMastered(userId, subtopicId);
    }

    @Override
    public String createNewUserInterestedSubtopic(Long userId, String subtopicId) {
        logger.info("Starting createNewUserInterestedSubtopic");

        String tempSubtopicId = incrementSubtopicId(subtopicId);
        String generateNextSubTopicId = userTopicMasteryDao.checkSubtopicExist(tempSubtopicId) ? tempSubtopicId : null;

        if (generateNextSubTopicId != null) {

            setUserSubTopicInterest(userId, generateNextSubTopicId);
            return generateNextSubTopicId;

        } else {
            String uncompletedSubtopicId = userTopicMasteryDao.getTopUserUncompletedTopic(userId);

            if (uncompletedSubtopicId != null) {
                setUserSubTopicInterest(userId, uncompletedSubtopicId);
                return uncompletedSubtopicId;

            } else {
                String randomSubtopicId = userTopicMasteryDao.getRandomUninterestedTopic(userId);
                setUserSubTopicInterest(userId, randomSubtopicId);
                return randomSubtopicId;
            }
        }
    }

    @Override
    public void setUserSubTopicInterest(Long userId, String subtopicId){
        logger.info("Starting setUserSubTopicInterest");

        userTopicDao.insertUserInterestedTopic(userId, subtopicId);
        userTopicMasteryDao.insertInitialTopicProgress(userId, subtopicId);
    }

    public static String incrementSubtopicId(String subtopicId){
        logger.info("Starting incrementSubtopicId");

        String prefix = subtopicId.replaceAll("\\d+$", "");
        String numberPart = subtopicId.replaceAll("\\D+", "");

        // Increment numeric part
        int nextNumber = Integer.parseInt(numberPart) + 1;

        // Preserve zero padding (same length as original number part)
        String padded = String.format("%0" + numberPart.length() + "d", nextNumber);

        return prefix + padded;
    }

    @Override
    public boolean moreLessonExists(Long userId, String subtopicId){
        logger.info("Starting moreLessonExists");

        int currentChain = progressService.getCurrentChain(userId, subtopicId);
        int targetNodeIndex = 6 + currentChain * 3; // 5 tutorial lesson + 3 standard lesson per chain + 1

        return topicDao.existsByNodeIndex(subtopicId, targetNodeIndex, "LESSON");
    }

    @Override
    public String getNodeId(String subtopicId, String nodeType, int contentSequence, int currentChain){
        logger.info("Starting getNodeId");

        int nextChain = currentChain + 1;
        int targetOrderIndex;

        // Tutorial currentChain == 1
        if (currentChain == 1){
            targetOrderIndex = contentSequence;
        }
        else{
            targetOrderIndex = (contentSequence - 1) + (nextChain * 3); // 3 standard lesson per chain
        }

        return topicDao.getNodeId(subtopicId, nodeType, targetOrderIndex);
    }

    @Override
    public void insertNewSubtopicMastery(Long userId, String subtopicId){
        logger.info("Starting insertNewSubtopicMastery");

        float p_know = topicDao.getInitialPKnow(subtopicId);

        userTopicMasteryDao.insertNewSubtopicMastery(userId, subtopicId, p_know);

    }

}
