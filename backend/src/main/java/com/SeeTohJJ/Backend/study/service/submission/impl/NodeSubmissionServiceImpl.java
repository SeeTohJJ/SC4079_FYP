package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.study.service.submission.NodeSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NodeSubmissionServiceImpl implements NodeSubmissionService {
    private static final Logger logger = LoggerFactory.getLogger(NodeSubmissionServiceImpl.class);
    private final UserStudyPathService userStudyPathService;
    private final NodeGenerationService nodeGenerationService;

    public NodeSubmissionServiceImpl(UserStudyPathService userStudyPathService, NodeGenerationService nodeGenerationService) {
        this.userStudyPathService = userStudyPathService;
        this.nodeGenerationService = nodeGenerationService;
    }


    @Override
    public void completeLesson(Long userId, String nodeId) {
        logger.info("Starting completeLesson");

        userStudyPathService.completeNode(userId, nodeId);

        int currentIndex = userStudyPathService.getNodePositionIndexInPath(userId, nodeId);

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
