package com.SeeTohJJ.Backend.study.service.submission.impl;

import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.study.service.submission.NodeSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NodeSubmissionServiceImpl implements NodeSubmissionService {
    private static final Logger logger = LoggerFactory.getLogger(NodeSubmissionServiceImpl.class);
    private final ProgressService progressService;
    private final NodeGenerationService nodeGenerationService;

    public NodeSubmissionServiceImpl(ProgressService progressService, NodeGenerationService nodeGenerationService) {
        this.progressService = progressService;
        this.nodeGenerationService = nodeGenerationService;
    }


    @Override
    public void completeLesson(Long userId, String nodeId) {
        logger.info("Starting completeLesson");

        progressService.completeNode(userId, nodeId);

        int nextNodeIndex = progressService.getNodePositionIndexInPath(userId, nodeId) + 1;

        if (progressService.checkIfNextNodeExist(
                userId,
                nextNodeIndex
        )) {
            progressService.unlockNextNode(userId, nextNodeIndex);
        }
        else {
            nodeGenerationService.generateNewChain(userId);
        }
    }
}
