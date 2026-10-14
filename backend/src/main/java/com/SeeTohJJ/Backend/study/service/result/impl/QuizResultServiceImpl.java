package com.SeeTohJJ.Backend.study.service.result.impl;

import com.SeeTohJJ.Backend.study.dao.impl.NodeContentDaoImpl;
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
    private final NodeContentDaoImpl nodeContentDaoImpl;

    public QuizResultServiceImpl(UserTopicService userTopicService, TopicService topicService, FeedbackService feedbackService, NodeContentDaoImpl nodeContentDaoImpl) {
        this.userTopicService = userTopicService;
        this.topicService = topicService;
        this.feedbackService = feedbackService;
        this.nodeContentDaoImpl = nodeContentDaoImpl;
    }


    @Override
    public QuizResultResponseDTO buildQuizResult(Long userId,
                                                 String topicId,
                                                 boolean correct,
                                                 int previousMastery,
                                                 int updatedMastery,
                                                 boolean newChainCreated,
                                                 int timeTaken,
                                                 int waterReward,
                                                 String nodeId) {
        logger.info("Starting buildQuizResult");

        QuizResultResponseDTO quizResultResponseDTO = new QuizResultResponseDTO();

        quizResultResponseDTO.setCorrect(correct);
        quizResultResponseDTO.setPreviousMastery(previousMastery);
        quizResultResponseDTO.setUpdatedMastery(updatedMastery);
        quizResultResponseDTO.setNewChainGenerated(newChainCreated);
        quizResultResponseDTO.setTopicName(topicService.getTopicName(topicId));
        quizResultResponseDTO.setNextReviewDate(userTopicService.getNextReviewDate(userId, topicId));

        String feedback;

        if (nodeId != null) {
            feedback = correct
                    ? feedbackService.generateFeedback(true, updatedMastery, timeTaken)
                    : nodeContentDaoImpl.getQuizExplanation(nodeId);
        } else {
            feedback = feedbackService.generateFeedback(
                    correct, updatedMastery, timeTaken
            );
        }

        quizResultResponseDTO.setFeedback(feedback);
        quizResultResponseDTO.setWaterReward(waterReward);
        return quizResultResponseDTO;
    }

}
