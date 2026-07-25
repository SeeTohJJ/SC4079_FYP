package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.result.QuizResultDTO;
import com.SeeTohJJ.Backend.study.service.adaptive.*;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.study.service.submission.QuizSubmissionService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private static final Logger logger = LoggerFactory.getLogger(QuizSubmissionServiceImpl.class);

    private final NodeGenerationService nodeGenerationService;
    private final BktService bktService;
    private final EloService eloService;
    private final SubTopicService subTopicService;
    private final StudyDao studyDao;
    private final ProgressService progressService;
    private final TopicService topicService;
    private final ForgettingService forgettingService;
    private final ContentRetrievalService contentRetrievalService;
    private final AttemptHistoryService attemptHistoryService;
    private final ConfidenceService confidenceService;

    @Autowired
    public QuizSubmissionServiceImpl(NodeGenerationService nodeGenerationService,
                                     BktService bktService,
                                     EloService eloService,
                                     SubTopicService subTopicService,
                                     StudyDao studyDao,
                                     ProgressService progressService,
                                     TopicService topicService,
                                     ForgettingService forgettingService,
                                     ContentRetrievalService contentRetrievalService,
                                     AttemptHistoryService attemptHistoryService, ConfidenceService confidenceService) {
        this.nodeGenerationService = nodeGenerationService;
        this.bktService = bktService;
        this.eloService = eloService;
        this.subTopicService = subTopicService;
        this.studyDao = studyDao;
        this.progressService = progressService;
        this.topicService = topicService;
        this.forgettingService = forgettingService;
        this.contentRetrievalService = contentRetrievalService;
        this.attemptHistoryService = attemptHistoryService;
        this.confidenceService = confidenceService;
    }

    @Override
    public boolean gradeAnswer(String nodeId, String optionSelected){
        logger.info("Starting gradeAnswer");

        return studyDao.getCorrectAnswer(nodeId).equals(optionSelected);
    }

    @Override
    public void completeQuiz(Long userId, QuizResultDTO quizResult){
        logger.info("Starting completeQuiz");

        String nodeId = quizResult.getNodeId();
        int timeTaken = quizResult.getTimeTaken();
        boolean isCorrectAnswer = gradeAnswer(quizResult.getNodeId(), quizResult.getOptionSelected());
        String subtopicId = subTopicService.getSubTopicId(nodeId);
        boolean hintUsed = quizResult.isHintUsed();

        attemptHistoryService.saveQuestionAttemptHistory(userId, nodeId, isCorrectAnswer, timeTaken, hintUsed);
        forgettingService.updateForgettingDecay(userId, subtopicId);
        bktService.updateUserKnowledge(
                userId,
                subtopicId,
                isCorrectAnswer,
                timeTaken,
                confidenceService.getConfidence(userId, nodeId, timeTaken,  hintUsed)
        );
        eloService.updateUserElo(userId, subtopicId, nodeId, isCorrectAnswer);
        bktService.updateSubTopicMastery(userId, subtopicId);
        processQuizCompletion(userId, nodeId);
    }

    public void processQuizCompletion(Long userId, String nodeId){
        logger.info("Starting processQuizCompletion");

        progressService.completeNode(userId, nodeId);
        int currentNodePosIndex = progressService.getNodePositionIndexInPath(userId, nodeId);

        if (nodeId.contains("S001-Q-003")){
            progressService.completeTutorialForInterestedTopic(userId, topicService.getTopicId(nodeId));
        }

        // Unlock next node if it exists
        if(progressService.checkIfNextNodePosExist(userId, currentNodePosIndex)) {
            progressService.unlockNextNode(userId, currentNodePosIndex);
        }

        // Generate new chain if there is no next node in progress
        else {
            nodeGenerationService.generateNewChain(userId);
            progressService.unlockNextNode(userId, currentNodePosIndex);
        }

    }

    @Override
    public String getQuizHint(Long userId, String nodeId){
        logger.info("Starting getQuizHint");

        attemptHistoryService.markHintUsed(userId, nodeId);

        return contentRetrievalService.getHint(nodeId);
    }

    @Override
    public String getQuizExplanation(String nodeId){
        logger.info("Starting getQuizExplanation");

        return contentRetrievalService.getExplanation(nodeId);
    }



}
