package com.SeeTohJJ.Backend.study.service.progress;

// Related to study node path and unlocking nodes
public interface ProgressService {

    void completeNode(Long userId, String nodeId);
    void unlockNextNode(Long userId, int nodePosIndex);
    int getNodePositionIndexInPath(Long userId, String nodeId);
    boolean checkIfNextNodePosExist(Long userId, int nodePosIndex);
    String getCurrentSubtopic(Long userId);
    void insertNodeIntoUserProgress(Long userId, String nodeId, int currentPathPositionIndex, boolean unlock, String nodeType);
    void completeTutorialForInterestedTopic(Long userId, String subtopicId);
}
