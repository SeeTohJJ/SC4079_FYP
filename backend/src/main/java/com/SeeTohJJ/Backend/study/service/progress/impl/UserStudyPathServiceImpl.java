package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.study.dao.StudyPathDao;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.user.dao.UserInterestedTopicsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStudyPathServiceImpl implements UserStudyPathService {

    private static final Logger logger = LoggerFactory.getLogger(UserStudyPathServiceImpl.class);

    private final StudyPathDao studyPathDao;
    private final UserInterestedTopicsDao userInterestedTopicsDao;

    @Autowired
    public UserStudyPathServiceImpl(StudyPathDao studyPathDao,
                                    UserInterestedTopicsDao userInterestedTopicsDao) {
        this.studyPathDao = studyPathDao;
        this.userInterestedTopicsDao = userInterestedTopicsDao;
    }

    @Override
    public void completeNode(Long userId, String nodeId){
        logger.info("Starting completeNode");

        studyPathDao.completeNode(userId, nodeId);
    }

    @Override
    public void unlockNextNode(Long userId, int currentNodePosIndex) {
        logger.info("Starting unlockNextNode");

        studyPathDao.unlockNextNode(userId, currentNodePosIndex + 1);
    }

    @Override
    public int getNodePositionIndexInPath(Long userId, String nodeId) {
        logger.info("Starting getNodePositionIndexInPath");

        return studyPathDao.getNodePositionalIndex(userId, nodeId);
    }

    @Override
    public boolean checkIfNextNodePosExist(Long userId, int nodePosIndex) {
        logger.info("Starting checkIfNextNodePosExist");

        return studyPathDao.checkIfNodeExistInProgress(userId, nodePosIndex + 1);
    }

    @Override
    public String getCurrentSubtopic(Long userId){
        logger.info("Starting getCurrentSubtopic");

        return studyPathDao.getCurrentSubtopic(userId);
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

        studyPathDao.insertNodeIntoUserProgress(userId, nodeId, nodeType, currentPathPositionIndex, unlock, false);
    }

    @Override
    public void completeTutorialForInterestedTopic(Long userId, String subtopicId) {
        logger.info("Starting completeTutorialForInterestedTopic");

        userInterestedTopicsDao.completeTutorialForInterestedTopic(userId, subtopicId);
    }

    @Override
    public List<String> getIncorrectNodes(Long userId, String subtopicId, int reviewNodeCount){
        logger.info("Starting getIncorrectNodes");

        return studyPathDao.getIncorrectNodes(userId, subtopicId, reviewNodeCount);
    }



}
