package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.garden.service.GardenService;
import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;
import com.SeeTohJJ.Backend.study.dto.result.QuizSubmissionDTO;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.service.adaptive.*;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.study.service.progress.UserTopicService;
import com.SeeTohJJ.Backend.study.service.result.QuizResultService;
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
    private final QuizResultService quizResultService;
    private final UserTopicService userTopicService;
    private final GardenService gardenService;

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
                                     AttemptHistoryService attemptHistoryService,
                                     ConfidenceService confidenceService, QuizResultService quizResultService, UserTopicService userTopicService, GardenService gardenService) {
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
        this.quizResultService = quizResultService;
        this.userTopicService = userTopicService;
        this.gardenService = gardenService;
    }

    @Override
    public boolean gradeAnswer(String nodeId, String optionSelected){
        logger.info("Starting gradeAnswer");

        return studyDao.getCorrectAnswer(nodeId).equals(optionSelected);
    }

    @Override
    public QuizResultResponseDTO completeQuiz(Long userId, QuizSubmissionDTO quizResult){
        logger.info("Starting completeQuiz");

        String nodeId = quizResult.getNodeId();
        int timeTaken = quizResult.getTimeTaken();
        boolean isCorrectAnswer = gradeAnswer(quizResult.getNodeId(), quizResult.getOptionSelected());
        String subtopicId = subTopicService.getSubTopicId(nodeId);
        boolean hintUsed = quizResult.isHintUsed();
        String topicId = subtopicId.substring(0, 4);
        double pastPKnow = userTopicService.getAveragePKnow(userId, topicId);

        attemptHistoryService.saveQuestionAttemptHistory(userId, nodeId, isCorrectAnswer, timeTaken, hintUsed);
        forgettingService.updateForgettingDecay(userId, subtopicId);
        bktService.runBktModel(
                userId,
                subtopicId,
                isCorrectAnswer,
                timeTaken,
                confidenceService.getConfidence(userId, nodeId, timeTaken,  hintUsed)
        );
        eloService.updateUserElo(userId, subtopicId, nodeId, isCorrectAnswer);

        boolean newChainCreated = processQuizCompletion(userId, nodeId);
//        gardenService.onStudyCompleted(userId, topicId, StudyNode.NodeType.QUIZ, isCorrectAnswer);

        return quizResultService.buildQuizResult(userId, topicId, isCorrectAnswer, pastPKnow, userTopicService.getAveragePKnow(userId, topicId), newChainCreated, timeTaken);
    }

    public boolean processQuizCompletion(Long userId, String nodeId){
        logger.info("Starting processQuizCompletion");

        progressService.completeNode(userId, nodeId);
        int currentNodePosIndex = progressService.getNodePositionIndexInPath(userId, nodeId);
        boolean newChainCreated = false;

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
            newChainCreated = true;
        }

        return newChainCreated;

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
