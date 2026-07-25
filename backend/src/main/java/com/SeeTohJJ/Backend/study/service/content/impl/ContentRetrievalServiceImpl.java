package com.SeeTohJJ.Backend.study.service.content.impl;

import com.SeeTohJJ.Backend.study.dao.NodeContentDao;
import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentRetrievalServiceImpl implements ContentRetrievalService {
    private static final Logger logger = LoggerFactory.getLogger(ContentRetrievalServiceImpl.class);

    private final NodeContentDao nodeContentDao;

    @Autowired
    public ContentRetrievalServiceImpl(NodeContentDao nodeContentDao) {
        this.nodeContentDao  = nodeContentDao;
    }

    @Override
    public LessonContentDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        return nodeContentDao.getLessonNodeContent(nodeId);
    }

    @Override
    public QuizContentDTO getQuizContent(String nodeId){
        logger.info("Starting getQuizContent");

        return nodeContentDao.getQuizNodeContent(nodeId);
    }

    @Override
    public DecisionContentDTO getDecisionNodeContent(String nodeId){
        logger.info("Starting getDecisionNodeContent");

        return nodeContentDao.getDecisionNodeContent(nodeId);
    }

    @Override
    public EventContentDTO getEventNodeContent(String nodeId){
        logger.info("Starting getEventNodeContent");

        return nodeContentDao.getEventNodeContent(nodeId);
    }

    @Override
    public double getQuestionRating(String nodeId){
        logger.info("Starting getQuestionRating");

        return nodeContentDao.getQuestionRating(nodeId);
    }

    @Override
    public String getHint(String nodeId){
        logger.info("Starting getHint");

        return nodeContentDao.getQuizHint(nodeId);
    }

    @Override
    public String getExplanation(String nodeId){
        logger.info("Starting getExplanation");

        return nodeContentDao.getQuizExplanation(nodeId);
    }

}
