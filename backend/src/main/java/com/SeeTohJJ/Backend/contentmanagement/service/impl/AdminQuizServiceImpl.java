package com.SeeTohJJ.Backend.contentmanagement.service.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.QuizRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.QuizResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminQuizService;
import com.SeeTohJJ.Backend.study.dao.NodeContentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminQuizServiceImpl implements AdminQuizService {

    private static final Logger logger = LoggerFactory.getLogger(AdminQuizServiceImpl.class);
    private final NodeContentDao nodeContentDao;

    @Autowired
    public AdminQuizServiceImpl(NodeContentDao nodeContentDao) {
        this.nodeContentDao = nodeContentDao;
    }

    @Override
    public List<QuizResponseDTO> getAllActiveQuizzes(){
        logger.info("Starting getAllActiveQuizzes");

        return nodeContentDao.getAllActiveQuizzes();
    }

    @Override
    public List<QuizResponseDTO> getAllInactiveQuizzes(){
        logger.info("Starting getAllInactiveQuizzes");

        return nodeContentDao.getAllInactiveQuizzes();
    }

    @Override
    public QuizResponseDTO getQuiz(String nodeId){
        logger.info("Starting getQuiz");

        return nodeContentDao.getQuiz(nodeId);
    }

    @Override
    public QuizResponseDTO createQuiz(QuizRequest request){
        logger.info("Starting createQuiz");

        String topicId = request.getTopicId();
        String subtopicId = request.getSubtopicId();
        String title = request.getTitle();
        int orderIndex = request.getOrderIndex();
        int requiredMastery = request.getRequiredMastery();
        String content = request.getContent();
        String optionA = request.getOptionA();
        String optionB = request.getOptionB();
        String optionC = request.getOptionC();
        String optionD = request.getOptionD();
        String correctAnswer = request.getCorrectAnswer();
        int difficultyRating =  request.getDifficultyRating();
        String hint  = request.getHint();
        String explanation = request.getExplanation();

        String nodeId = nodeContentDao.findNextNodeId(subtopicId, "QUIZ");
        nodeContentDao.createQuiz(nodeId, topicId, subtopicId, title, orderIndex, requiredMastery, content,
                optionA, optionB, optionC, optionD, correctAnswer, difficultyRating, hint, explanation);

        return nodeContentDao.getQuiz(nodeId);
    }

    @Override
    public QuizResponseDTO updateQuiz(String nodeId, QuizRequest request){
        logger.info("Starting updateQuiz");

        String topicId = request.getTopicId();
        String subtopicId = request.getSubtopicId();
        String title = request.getTitle();
        int orderIndex = request.getOrderIndex();
        int requiredMastery = request.getRequiredMastery();
        String content = request.getContent();
        String optionA = request.getOptionA();
        String optionB = request.getOptionB();
        String optionC = request.getOptionC();
        String optionD = request.getOptionD();
        String correctAnswer = request.getCorrectAnswer();
        int difficultyRating =  request.getDifficultyRating();
        String hint  = request.getHint();
        String explanation = request.getExplanation();

        nodeContentDao.updateQuiz(nodeId, topicId, subtopicId, title, orderIndex, requiredMastery, content,
                optionA, optionB, optionC, optionD, correctAnswer, difficultyRating, hint, explanation);

        return nodeContentDao.getQuiz(nodeId);
    }

    @Override
    public void setQuizInactive(String nodeId){
        logger.info("Starting setQuizInactive");

        nodeContentDao.setQuizInactive(nodeId);
    }

    @Override
    public void setQuizActive(String nodeId){
        logger.info("Starting setQuizActive");

        nodeContentDao.setQuizActive(nodeId);
    }

    @Override
    public int getActiveCount(){
        logger.info("Starting getActiveCount");

        return nodeContentDao.getActiveCount("QUIZ");
    }

}
