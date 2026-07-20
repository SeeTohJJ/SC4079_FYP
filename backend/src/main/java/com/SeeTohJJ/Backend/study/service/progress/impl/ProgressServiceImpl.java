package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressServiceImpl implements ProgressService {

    private static final Logger logger = LoggerFactory.getLogger(ProgressServiceImpl.class);

    private final StudyDao studyDao;

    @Autowired
    public ProgressServiceImpl(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    @Override
    public void completeNode(Long userId, String nodeId){
        logger.info("Starting completeNode");

        studyDao.completeNode(userId, nodeId);
    }

    @Override
    public void unlockNextNode(Long userId, int nodePosIndex) {
        logger.info("Starting unlockNextNode");

        studyDao.unlockNextNode(userId, nodePosIndex);
    }

    @Override
    public int getNodePositionIndexInPath(Long userId, String nodeId) {
        logger.info("Starting getNodePositionIndexInPath");

        return studyDao.getNodePositionalIndex(userId, nodeId);
    }

    @Override
    public boolean checkIfNextNodeExist(Long userId, int nodePosIndex) {
        logger.info("Starting checkIfNextNodeExist");

        return studyDao.checkIfNextNodeExist(userId, nodePosIndex);
    }

    @Override
    public String getCurrentSubtopic(Long userId){
        logger.info("Starting getCurrentSubtopic");

        return studyDao.getCurrentSubtopic(userId);
    }

    @Override
    public String getUserLowestPKnowSubtopic(Long userId){
        logger.info("Starting getUserLowestPKnowSubtopic");

        return studyDao.getLowestPKnowSubtopic(userId);
    }

    @Override
    public String getUserLowestPKnowSubtopicNotMastered(Long userId){
        logger.info("Starting getUserLowestPKnowSubtopicNotMastered");

        return studyDao.getLowestPKnowSubtopicNotMastered(userId);
    }

    @Override
    public int getCurrentChain(Long userId, String subtopicId) {
        logger.info("Starting getCurrentChain");

        return studyDao.getCurrentChain(userId, subtopicId);
    }

    @Override
    public void insertNodeIntoUserProgress(Long userId, String nodeId, int currentPathPositionIndex, boolean unlock, String nodeType){
        logger.info("Starting insertNodeIntoUserProgress");

        studyDao.insertNodeIntoUserProgress(userId, nodeId, nodeType, currentPathPositionIndex, unlock, false);
    }


}
