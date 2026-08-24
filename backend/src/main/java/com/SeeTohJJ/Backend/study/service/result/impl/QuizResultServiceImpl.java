package com.SeeTohJJ.Backend.study.service.result.impl;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;
import com.SeeTohJJ.Backend.user.service.mastery.UserTopicService;
import com.SeeTohJJ.Backend.study.service.result.FeedbackService;
import com.SeeTohJJ.Backend.study.service.result.QuizResultService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuizResultServiceImpl implements QuizResultService {

    private static final Logger logger = LoggerFactory.getLogger(QuizResultServiceImpl.class);

    private final UserTopicService userTopicService;
    private final TopicService topicService;
    private final FeedbackService feedbackService;

    public QuizResultServiceImpl(UserTopicService userTopicService, TopicService topicService, FeedbackService feedbackService) {
        this.userTopicService = userTopicService;
        this.topicService = topicService;
        this.feedbackService = feedbackService;
    }


    @Override
    public QuizResultResponseDTO buildQuizResult(Long userId,
                                                 String topicId,
                                                 boolean correct,
                                                 int previousMastery,
                                                 int updatedMastery,
                                                 boolean newChainCreated,
                                                 int timeTaken,
                                                 int waterReward) {
        logger.info("Starting buildQuizResult");

        QuizResultResponseDTO quizResultResponseDTO = new QuizResultResponseDTO();

        quizResultResponseDTO.setCorrect(correct);
        quizResultResponseDTO.setPreviousMastery(previousMastery);
        quizResultResponseDTO.setUpdatedMastery(updatedMastery);
        quizResultResponseDTO.setNewChainGenerated(newChainCreated);
        quizResultResponseDTO.setTopicName(topicService.getTopicName(topicId));
        quizResultResponseDTO.setNextReviewDate(userTopicService.getNextReviewDate(userId, topicId));
        quizResultResponseDTO.setFeedback(feedbackService.generateFeedback(correct, updatedMastery, timeTaken));
        quizResultResponseDTO.setWaterReward(waterReward);
        return quizResultResponseDTO;
    }

}
