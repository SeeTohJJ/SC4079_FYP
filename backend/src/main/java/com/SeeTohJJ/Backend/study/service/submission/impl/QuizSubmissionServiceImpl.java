package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.result.QuizResultDTO;
import com.SeeTohJJ.Backend.study.service.adaptive.BktService;
import com.SeeTohJJ.Backend.study.service.adaptive.EloService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.study.service.submission.QuizSubmissionService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
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

    @Autowired
    public QuizSubmissionServiceImpl(NodeGenerationService nodeGenerationService,
                                     BktService bktService,
                                     EloService eloService,
                                     SubTopicService subTopicService,
                                     StudyDao studyDao, ProgressService progressService) {
        this.nodeGenerationService = nodeGenerationService;
        this.bktService = bktService;
        this.eloService = eloService;
        this.subTopicService = subTopicService;
        this.studyDao = studyDao;
        this.progressService = progressService;
    }

    @Override
    public boolean gradeAnswer(String nodeId, String optionSelected){
        logger.info("Starting gradeAnswer");

        return studyDao.getCorrectAnswer(nodeId).equals(optionSelected);
    }

    @Override
    public void saveQuestionAttemptHistory(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken){
        logger.info("Starting saveQuestionAttemptHistory");

        studyDao.saveUserQuestionAttempt(userId, nodeId, isCorrectAnswer, timeTaken);
    }

    @Override
    public void submitReviewQuiz(QuizResultDTO quizResult){
        logger.info("Starting submitReviewQuiz");

        Long userId = quizResult.getUserId();
        String nodeId = quizResult.getNodeId();
        int timeTaken = quizResult.getTimeTaken();
        boolean isCorrectAnswer = gradeAnswer(quizResult.getNodeId(), quizResult.getOptionSelected());
        String subtopicId = subTopicService.getSubTopicId(nodeId);

        saveQuestionAttemptHistory(userId, nodeId, isCorrectAnswer, timeTaken);
        bktService.updateUserKnowledge(userId, subtopicId, isCorrectAnswer, timeTaken);
        eloService.updateUserElo(userId, subtopicId, isCorrectAnswer);
        bktService.updateSubTopicMastery(userId, subtopicId);
        processQuizCompletion(userId, nodeId);
    }

    public void processQuizCompletion(Long userId, String nodeId){
        logger.info("Starting processQuizCompletion");

        progressService.completeNode(userId, nodeId);
        int nodePosIndex = progressService.getNodePositionIndexInPath(userId, nodeId);

        if(progressService.checkIfNextNodeExist(userId, nodePosIndex + 1)) {
            progressService.unlockNextNode(userId, nodePosIndex + 1);
        }
        else if(nodeId.contains("MRQ")){
            nodeGenerationService.generateNewChain(userId);
            progressService.unlockNextNode(userId, nodePosIndex + 1);
        }

    }


}
