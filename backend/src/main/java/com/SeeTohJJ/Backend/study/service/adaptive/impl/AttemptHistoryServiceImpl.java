package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.dao.AttemptHistoryDao;
import com.SeeTohJJ.Backend.study.service.adaptive.AttemptHistoryService;
import com.SeeTohJJ.Backend.study.service.progress.UserSubtopicService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AttemptHistoryServiceImpl implements AttemptHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(AttemptHistoryServiceImpl.class);
    private final SubTopicService subTopicService;
    private final UserSubtopicService userSubtopicService;
    private final AttemptHistoryDao attemptHistoryDao;

    public AttemptHistoryServiceImpl(SubTopicService subTopicService,
                                     UserSubtopicService userSubtopicService,
                                     AttemptHistoryDao attemptHistoryDao) {
        this.subTopicService = subTopicService;
        this.userSubtopicService = userSubtopicService;
        this.attemptHistoryDao = attemptHistoryDao;
    }

    @Override
    public void markHintUsed(Long userId, String nodeId){
        logger.info("Starting markHintUsed");

        String subtopicId = subTopicService.getSubTopicId(nodeId);
        userSubtopicService.incrementHintUsage(userId, subtopicId);
    }

    @Override
    public void saveQuestionAttemptHistory(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken, boolean hintUsed) {
        logger.info("Starting saveQuestionAttemptHistory");

        attemptHistoryDao.saveUserQuestionAttempt(userId, nodeId, isCorrectAnswer, timeTaken, hintUsed);
    }

    @Override
    public int getQuizAttemptHistoryCount(Long userId, String nodeId) {
        logger.info("Starting getQuizAttemptHistoryCount");

        return attemptHistoryDao.getQuizAttemptHistoryCount(userId, nodeId);
    }


}
