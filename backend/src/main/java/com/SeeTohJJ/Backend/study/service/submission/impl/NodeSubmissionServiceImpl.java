package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.garden.service.GardenService;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.study.service.submission.NodeSubmissionService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NodeSubmissionServiceImpl implements NodeSubmissionService {
    private static final Logger logger = LoggerFactory.getLogger(NodeSubmissionServiceImpl.class);
    private final UserStudyPathService userStudyPathService;
    private final NodeGenerationService nodeGenerationService;
    private final GardenService gardenService;
    private final TopicService topicService;

    public NodeSubmissionServiceImpl(UserStudyPathService userStudyPathService, NodeGenerationService nodeGenerationService, GardenService gardenService, TopicService topicService) {
        this.userStudyPathService = userStudyPathService;
        this.nodeGenerationService = nodeGenerationService;
        this.gardenService = gardenService;
        this.topicService = topicService;
    }


    @Override
    public void completeLesson(Long userId, String nodeId) {
        logger.info("Starting completeLesson");

        userStudyPathService.completeNode(userId, nodeId);

        int currentIndex = userStudyPathService.getNodePositionIndexInPath(userId, nodeId);
        gardenService.onNodeCompleted(userId, topicService.getTopicId(nodeId), StudyNode.NodeType.LESSON, false);

        if (userStudyPathService.checkIfNextNodePosExist(
                userId,
                currentIndex
        )) {
            userStudyPathService.unlockNextNode(userId, currentIndex);
        }
        else {
            nodeGenerationService.generateNewChain(userId);
        }
    }
}
