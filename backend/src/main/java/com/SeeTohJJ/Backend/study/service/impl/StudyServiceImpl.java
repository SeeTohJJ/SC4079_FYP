package com.SeeTohJJ.Backend.study.service.impl;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.dao.StudyDao;
import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.dto.option.DecisionOptionDTO;
import com.SeeTohJJ.Backend.study.dto.option.QuizOptionDTO;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;
import com.SeeTohJJ.Backend.study.service.StudyService;
import com.SeeTohJJ.Backend.topic.service.TopicService;

import com.SeeTohJJ.Backend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudyServiceImpl implements StudyService {

    private static final Logger logger = LoggerFactory.getLogger(StudyServiceImpl.class);

    private final StudyDao studyDao;
    private final TopicService topicService;

    @Autowired
    public StudyServiceImpl(StudyDao studyDao,  TopicService topicService, UserService userService) {
        this.studyDao = studyDao;
        this.topicService = topicService;
    }

    @Override
    public List<StudyNodePathDTO> getStudyPathNodes(Long userId){
        logger.info("Starting getStudyPathNodes");

        // Check and get nodes from user_node_progress if there is one that is unlocked AND not completed
        if (studyDao.hasActiveNodes(userId)) {
            return getExistingNodePath(userId);
        }

        String uncompletedTopicId = topicService.getUncompletedTutorialTopic(userId);

        // Generate study nodes based on user topic interest
//        if(uncompletedTopicId == null) {
//            insertAdaptiveNodesForUser(userId);
//
//            return getExistingNodePath(userId);
//        }

        // Generate tutorial nodes if user has interest in topic and tutorial not completed
        insertTutorialNodesForUser(userId, uncompletedTopicId);

        return getExistingNodePath(userId);
    }

    @Override
    public List<StudyNodePathDTO> getExistingNodePath(Long userId){
        logger.info("Starting getExistingNodePath");

        List<UserNodeProgress> nodePaths = studyDao.getExistingNodePath(userId);


        return nodePaths.stream()
                .map(this::convertToStudyNodePathDTO)
                .toList();
    }

    private StudyNodePathDTO convertToStudyNodePathDTO(
            UserNodeProgress nodeProgress) {

        StudyNodePathDTO dto = new StudyNodePathDTO();

        dto.setNodeId(nodeProgress.getNodeId());
        dto.setNodeType(nodeProgress.getNodeType());
        dto.setPositionIndex(nodeProgress.getPositionIndex());
        dto.setUnlocked(nodeProgress.isUnlocked());
        dto.setCompleted(nodeProgress.isCompleted());

        return dto;
    }


    private void insertAdaptiveNodesForUser(Long userId) {
        logger.info("Starting insertAdaptiveNodesForUser");

    }


    // Take tutorial node from study_node and insert it into user_node_progress
    private void insertTutorialNodesForUser(Long userId, String TopicId) {
        logger.info("Starting insertTutorialNodesForUser");

        List<StudyNode> nodes = fetchTutorialNodes(TopicId);
        insertNodesIntoNodeProgress(nodes, userId);
    }

    private List<StudyNode> fetchTutorialNodes(String TopicId) {
        logger.info("Starting fetchTutorialNodes");

        return studyDao.getTutorialNodes(TopicId);
    }

    private void insertNodesIntoNodeProgress(List<StudyNode> nodes, Long userId) {
        logger.info("Starting insertNodesIntoNodeProgress");

        int position = 0;

        for (StudyNode node : nodes) {

            boolean unlocked = (position == 0);

            studyDao.insertNodeIntoUserProgress(
                    userId,
                    node.getNodeId(),
                    node.getType().toString(),
                    position,
                    unlocked,
                    false
            );

            position++;
        }
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
