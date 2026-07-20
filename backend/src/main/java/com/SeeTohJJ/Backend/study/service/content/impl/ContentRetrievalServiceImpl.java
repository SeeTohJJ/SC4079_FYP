package com.SeeTohJJ.Backend.study.service.content.impl;

import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.progress.impl.NodeGenerationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentRetrievalServiceImpl implements ContentRetrievalService {
    private static final Logger logger = LoggerFactory.getLogger(ContentRetrievalServiceImpl.class);

    private final StudyDao studyDao;

    @Autowired
    public ContentRetrievalServiceImpl(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    @Override
    public LessonNodeDTO getLessonNodeContent(String nodeId){
        logger.info("Starting getLessonNodeContent");

        return studyDao.getLessonNodeContent(nodeId);
    }

    @Override
    public QuizNodeDTO getQuizContent(String nodeId){
        logger.info("Starting getQuizContent");

        return studyDao.getQuizNodeContent(nodeId);
    }

    @Override
    public DecisionNodeDTO getDecisionNodeContent(String nodeId){
        logger.info("Starting getDecisionNodeContent");

        return studyDao.getDecisionNodeContent(nodeId);
    }

    @Override
    public EventNodeDTO getEventNodeContent(String nodeId){
        logger.info("Starting getEventNodeContent");

        return studyDao.getEventNodeContent(nodeId);
    }

}
