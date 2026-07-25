package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.dao.AttemptHistoryDao;
import com.SeeTohJJ.Backend.study.service.adaptive.AttemptHistoryService;
import com.SeeTohJJ.Backend.study.service.adaptive.ConfidenceService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfidenceServiceImpl implements ConfidenceService {

    private static final Logger logger = LoggerFactory.getLogger(ConfidenceServiceImpl.class);

    private final AttemptHistoryService attemptHistoryService;
    private final SubTopicService subTopicService;

    @Autowired
    public ConfidenceServiceImpl(AttemptHistoryService attemptHistoryService, SubTopicService subTopicService) {
        this.attemptHistoryService = attemptHistoryService;
        this.subTopicService = subTopicService;
    }

    @Override
    public double getConfidence(Long userId, String nodeId, int timeTaken, boolean hintUsed){
        logger.debug("Starting getConfidence");

        int attempts = attemptHistoryService.getQuizAttemptHistoryCount(userId, nodeId);
        int questionDifficulty = subTopicService.getNodeDifficulty(nodeId);

        return calculateConfidence(timeTaken, attempts, hintUsed, questionDifficulty);
    }

    private double calculateConfidence(int timeTaken, int attempts, boolean hintUsed, int questionDifficulty){
        double timeScore = calculateTimeScore(timeTaken);
        double attemptScore = calculateAttemptScore(attempts);
        double hintScore = hintUsed ? 0.6 : 1.0;
        double difficultyScore = calculateDifficultyScore(questionDifficulty);

        return (timeScore +
                attemptScore +
                hintScore +
                difficultyScore) / 4.0;
    }

    private double calculateTimeScore(int timeTaken){

        if (timeTaken <= 10){
            return 1.0;
        } else if (timeTaken <= 20){
            return 0.9;
        } else if (timeTaken <= 40){
            return 0.7;
        } else if  (timeTaken <= 60){
            return 0.5;
        }
        return 0.3;
    }

    private double calculateAttemptScore(int attempts){
        if (attempts == 1){
            return 1.0;
        } else if (attempts == 2){
            return 0.8;
        } else if (attempts == 3){
            return 0.6;
        }
        return 0.4;
    }

    private double calculateDifficultyScore(int questionDifficulty){
        if (questionDifficulty >= 5){
            return 1.0;
        } else if (questionDifficulty == 4){
            return 0.8;
        } else if (questionDifficulty == 3){
            return 0.7;
        } else if (questionDifficulty == 2){
            return 0.6;
        } else if (questionDifficulty == 1){
            return 0.5;
        }
        return 0.4;
    }

}
