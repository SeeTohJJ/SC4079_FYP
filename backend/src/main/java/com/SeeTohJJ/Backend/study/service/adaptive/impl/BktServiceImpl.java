package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.service.adaptive.BktService;
import com.SeeTohJJ.Backend.study.service.adaptive.SpacedRepetitionService;
import com.SeeTohJJ.Backend.user.service.mastery.UserSubtopicService;
import com.SeeTohJJ.Backend.user.service.mastery.UserTopicService;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BktServiceImpl implements BktService {

    private static final Logger logger = LoggerFactory.getLogger(BktServiceImpl.class);

    private final SubTopicService subTopicService;
    private final UserSubtopicService userSubtopicService;
    private final UserTopicService userTopicService;
    private final SpacedRepetitionService spacedRepetitionService;

    public BktServiceImpl(SubTopicService subTopicService,
                          UserSubtopicService userSubtopicService,
                          UserTopicService userTopicService,
                          SpacedRepetitionService spacedRepetitionService) {
        this.subTopicService = subTopicService;
        this.userSubtopicService = userSubtopicService;
        this.userTopicService = userTopicService;
        this.spacedRepetitionService = spacedRepetitionService;
    }

    @Override
    public void runBktModel(Long userId, String subtopicId, boolean isCorrectAnswer, int timeTaken, double confidence) {
        logger.info("Starting runBktModel");

        updateUserKnowledge(userId, subtopicId, isCorrectAnswer, timeTaken, confidence);
        updateSubTopicMastery(userId, subtopicId);

        String topicId = subTopicService.getTopicIdFromSubtopicId(subtopicId);
        userTopicService.updateTopicMasteryAverage(userId, topicId);
        spacedRepetitionService.scheduleNextReview(userId, topicId, userTopicService.getAveragePKnow(userId, topicId));
    }

    @Override
    public void updateUserKnowledge(Long userId, String subtopicId, boolean isCorrectAnswer, int timeTaken, double confidence){
        logger.info("Starting updateUserKnowledge");

        BktParameters bktParameters = subTopicService.getBktParameters(subtopicId); // BKT parameters for the subtopic
        double oldPKnow = userSubtopicService.getUserPKnow(userId, subtopicId);

        double posterior;
        if(isCorrectAnswer){
            posterior = calculatePosteriorCorrect(oldPKnow, bktParameters.getP_slip(), bktParameters.getP_guess());
        }
        else{
            posterior = calculatePosteriorIncorrect(oldPKnow, bktParameters.getP_slip(), bktParameters.getP_guess());
        }

        double newPKnow = applyLearning(posterior, bktParameters.getP_transit());
        newPKnow = Math.clamp(newPKnow, 0, 1);

        double finalPKnow = scaleBktWithConfidence(oldPKnow, newPKnow, confidence);

        userSubtopicService.saveUserPKnow(userId, subtopicId, finalPKnow);
        userSubtopicService.updateAttemptStatistics(userId, subtopicId, isCorrectAnswer);
    }

    private double calculatePosteriorCorrect(double pKnow, double pSlip, double pGuess){
        logger.info("Starting calculatePosteriorCorrect");

        double numerator = pKnow * (1 - pSlip);
        double denominator = numerator + (1 - pKnow) * pGuess;

        return numerator / denominator;
    }

    private double calculatePosteriorIncorrect(double pKnow, double pSlip, double pGuess) {
        logger.info("Starting calculatePosteriorIncorrect");

        double numerator = pKnow * pSlip;
        double denominator = numerator + (1 - pKnow) * (1 - pGuess);

        return numerator / denominator;
    }

    private double applyLearning(double posterior, double pTransition){
        logger.info("Starting applyLearning");

        return posterior + (1 - posterior) * pTransition;
    }

    @Override
    public void updateSubTopicMastery(Long userId, String subtopicId){
        logger.info("Starting updateSubTopicMastery");

        if(userSubtopicService.pKnowGreaterThanRating(userId, subtopicId)){
            userSubtopicService.setSubtopicIsMasteredToTrue(userId, subtopicId);
        }
    }

    private double scaleBktWithConfidence(double oldPKnow, double newPKnow, double confidence){
        logger.info("Starting scaleBktWithConfidence");

        double scaledPKnow = oldPKnow + (newPKnow - oldPKnow) * confidence;
        return Math.clamp(scaledPKnow, 0, 1);
    }

}
