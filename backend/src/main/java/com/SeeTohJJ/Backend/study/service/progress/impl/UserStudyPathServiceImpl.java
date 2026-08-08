package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStudyPathServiceImpl implements UserStudyPathService {

    private static final Logger logger = LoggerFactory.getLogger(UserStudyPathServiceImpl.class);

    private final StudyDao studyDao;

    @Autowired
    public UserStudyPathServiceImpl(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    @Override
    public void completeNode(Long userId, String nodeId){
        logger.info("Starting completeNode");

        studyDao.completeNode(userId, nodeId);
    }

    @Override
    public void unlockNextNode(Long userId, int currentNodePosIndex) {
        logger.info("Starting unlockNextNode");

        studyDao.unlockNextNode(userId, currentNodePosIndex + 1);
    }

    @Override
    public int getNodePositionIndexInPath(Long userId, String nodeId) {
        logger.info("Starting getNodePositionIndexInPath");

        return studyDao.getNodePositionalIndex(userId, nodeId);
    }

    @Override
    public boolean checkIfNextNodePosExist(Long userId, int nodePosIndex) {
        logger.info("Starting checkIfNextNodePosExist");

        return studyDao.checkIfNodeExistInProgress(userId, nodePosIndex + 1);
    }

    @Override
    public String getCurrentSubtopic(Long userId){
        logger.info("Starting getCurrentSubtopic");

        return studyDao.getCurrentSubtopic(userId);
    }

//    @Override
//    public int getCurrentChain(Long userId, String subtopicId) {
//        logger.info("Starting getCurrentChain");
//
//        return studyDao.getCurrentChain(userId, subtopicId);
//    }

    @Override
    public void insertNodeIntoUserProgress(Long userId, String nodeId, int currentPathPositionIndex, boolean unlock, String nodeType){
        logger.info("Starting insertNodeIntoUserProgress");

        studyDao.insertNodeIntoUserProgress(userId, nodeId, nodeType, currentPathPositionIndex, unlock, false);
    }

    @Override
    public void completeTutorialForInterestedTopic(Long userId, String subtopicId) {
        logger.info("Starting completeTutorialForInterestedTopic");

        studyDao.completeTutorialForInterestedTopic(userId, subtopicId);
    }

    @Override
    public List<String> getIncorrectNodes(Long userId, String subtopicId, int reviewNodeCount){
        logger.info("Starting getIncorrectNodes");

        return studyDao.getIncorrectNodes(userId, subtopicId, reviewNodeCount);
    }



}
