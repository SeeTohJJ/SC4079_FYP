package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.service.adaptive.EloService;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.progress.UserSubtopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EloServiceImpl implements EloService {

    private static final Logger logger = LoggerFactory.getLogger(EloServiceImpl.class);

    private final UserSubtopicService userSubtopicService;
    private final ContentRetrievalService contentRetrievalService;

    public EloServiceImpl(UserSubtopicService userSubtopicService,
                          ContentRetrievalService contentRetrievalService) {
        this.userSubtopicService = userSubtopicService;
        this.contentRetrievalService = contentRetrievalService;
    }

    @Override
    public void updateUserElo(Long userId, String subtopicId, String nodeId, boolean isCorrectAnswer){
        logger.info("Starting updateUserElo");

        double questionRating = contentRetrievalService.getQuestionRating(nodeId);
        double userElo = userSubtopicService.getUserElo(userId, subtopicId);

        double expected = 1 / (1 + Math.pow(10, questionRating - userElo) / 400.0);
        double newElo = calculateNewElo(userElo, expected, isCorrectAnswer);
        userSubtopicService.setUserElo(userId, subtopicId, newElo);
    }

    private double calculateNewElo(double currentElo, double expected, boolean isCorrectAnswer) {
        logger.info("Starting calculateNewElo");

        int kFactor = 32;
        int actual = isCorrectAnswer ? 1 : 0;

        return currentElo + kFactor * (actual - expected);
    }


}
