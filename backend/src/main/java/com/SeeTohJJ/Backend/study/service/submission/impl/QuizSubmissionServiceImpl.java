package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.garden.service.GardenService;
import com.SeeTohJJ.Backend.study.dao.StudyPathDao;
import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;
import com.SeeTohJJ.Backend.study.dto.result.QuizSubmissionDTO;
import com.SeeTohJJ.Backend.study.service.adaptive.*;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.user.service.mastery.UserSubtopicService;
import com.SeeTohJJ.Backend.user.service.mastery.UserTopicService;
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
    private final StudyPathDao studyPathDao;
    private final UserStudyPathService userStudyPathService;
    private final TopicService topicService;
    private final ForgettingService forgettingService;
    private final ContentRetrievalService contentRetrievalService;
    private final AttemptHistoryService attemptHistoryService;
    private final ConfidenceService confidenceService;
    private final QuizResultService quizResultService;
    private final UserTopicService userTopicService;
    private final GardenService gardenService;
    private final UserSubtopicService userSubtopicService;

    @Autowired
    public QuizSubmissionServiceImpl(NodeGenerationService nodeGenerationService,
                                     BktService bktService,
                                     EloService eloService,
                                     SubTopicService subTopicService,
                                     StudyPathDao studyPathDao,
                                     UserStudyPathService userStudyPathService,
                                     TopicService topicService,
                                     ForgettingService forgettingService,
                                     ContentRetrievalService contentRetrievalService,
                                     AttemptHistoryService attemptHistoryService,
                                     ConfidenceService confidenceService, QuizResultService quizResultService, UserTopicService userTopicService, GardenService gardenService, UserSubtopicService userSubtopicService) {
        this.nodeGenerationService = nodeGenerationService;
        this.bktService = bktService;
        this.eloService = eloService;
        this.subTopicService = subTopicService;
        this.studyPathDao = studyPathDao;
        this.userStudyPathService = userStudyPathService;
        this.topicService = topicService;
        this.forgettingService = forgettingService;
        this.contentRetrievalService = contentRetrievalService;
        this.attemptHistoryService = attemptHistoryService;
        this.confidenceService = confidenceService;
        this.quizResultService = quizResultService;
        this.userTopicService = userTopicService;
        this.gardenService = gardenService;
        this.userSubtopicService = userSubtopicService;
    }

    @Override
    public boolean gradeAnswer(String nodeId, String optionSelected){
        logger.info("Starting gradeAnswer");

        return studyPathDao.getCorrectAnswer(nodeId).equals(optionSelected);
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

        logger.info("PastPKnow: " + userSubtopicService.getUserPKnow(userId, subtopicId));
        attemptHistoryService.saveQuestionAttemptHistory(userId, nodeId, isCorrectAnswer, timeTaken, hintUsed);
        logger.info("PastPKnow: " + userSubtopicService.getUserPKnow(userId, subtopicId));
        forgettingService.updateForgettingDecay(userId, subtopicId);
        logger.info("PastPKnow: " + userSubtopicService.getUserPKnow(userId, subtopicId));
        bktService.runBktModel(
                userId,
                subtopicId,
                isCorrectAnswer,
                timeTaken,
                confidenceService.getConfidence(userId, nodeId, timeTaken,  hintUsed)
        );
        logger.info("PastPKnow: " + userSubtopicService.getUserPKnow(userId, subtopicId));
        eloService.updateUserElo(userId, subtopicId, nodeId, isCorrectAnswer);
        logger.info("PastPKnow: " + userSubtopicService.getUserPKnow(userId, subtopicId));
        boolean newChainCreated = processQuizCompletion(userId, nodeId);
        logger.info("PastPKnow: " + userSubtopicService.getUserPKnow(userId, subtopicId));
        //        gardenService.onStudyCompleted(userId, topicId, StudyNode.NodeType.QUIZ, isCorrectAnswer);

        return quizResultService.buildQuizResult(userId, topicId, isCorrectAnswer, pastPKnow, userTopicService.getAveragePKnow(userId, topicId), newChainCreated, timeTaken);
    }

    public boolean processQuizCompletion(Long userId, String nodeId){
        logger.info("Starting processQuizCompletion");

        userStudyPathService.completeNode(userId, nodeId);
        int currentNodePosIndex = userStudyPathService.getNodePositionIndexInPath(userId, nodeId);
        boolean newChainCreated = false;

        if (nodeId.contains("S001-Q-003")){
            userStudyPathService.completeTutorialForInterestedTopic(userId, topicService.getTopicId(nodeId));
        }

        // Unlock next node if it exists
        if(userStudyPathService.checkIfNextNodePosExist(userId, currentNodePosIndex)) {
            userStudyPathService.unlockNextNode(userId, currentNodePosIndex);
        }

        // Generate new chain if there is no next node in progress
        else {
            nodeGenerationService.generateNewChain(userId);
            userStudyPathService.unlockNextNode(userId, currentNodePosIndex);
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
