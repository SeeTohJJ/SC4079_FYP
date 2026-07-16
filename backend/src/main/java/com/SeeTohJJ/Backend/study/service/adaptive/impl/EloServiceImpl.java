package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.service.adaptive.EloService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EloServiceImpl implements EloService {

    private static final Logger logger = LoggerFactory.getLogger(EloServiceImpl.class);
    private final SubTopicService subTopicService;
    private final StudyDao studyDao;

    public EloServiceImpl(SubTopicService subTopicService,  StudyDao studyDao) {
        this.subTopicService = subTopicService;
        this.studyDao = studyDao;
    }

    @Override
    public void updateUserElo(Long userId, String nodeId, boolean isCorrectAnswer){
        logger.info("Starting updateUserElo");

        String subtopicId = subTopicService.getSubTopicId(nodeId);
        double questionRating = studyDao.getQuestionRating(nodeId);
        double userElo = subTopicService.getUserElo(userId, subtopicId);

        double expected = 1 / (1 + Math.pow(10, questionRating - userElo) / 400.0);
        double newElo = calculateNewElo(userElo, expected, isCorrectAnswer);
        subTopicService.setUserNewEloRating(userId, subtopicId, newElo);
    }

    private double calculateNewElo(double currentElo, double expected, boolean isCorrectAnswer) {
        logger.info("Starting calculateNewElo");

        int kFactor = 32;
        int actual = isCorrectAnswer ? 1 : 0;

        return currentElo + kFactor * (actual - expected);
    }


}
