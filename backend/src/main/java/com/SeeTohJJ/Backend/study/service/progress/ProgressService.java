package com.SeeTohJJ.Backend.study.service.progress;

public interface ProgressService {

    void completeNode(Long userId, String nodeId);
    void unlockNextNode(Long userId, int nodePosIndex);
    int getNodePositionIndexInPath(Long userId, String nodeId);
    boolean checkIfNextNodeExist(Long userId, int nodePosIndex);
    String getCurrentSubtopic(Long userId);
    String getUserLowestPKnowSubtopic(Long userId);
    String getUserLowestPKnowSubtopicNotMastered(Long userId);
    int getCurrentChain(Long userId,  String subtopicId);
    void insertNodeIntoUserProgress(Long userId, String nodeId, int currentPathPositionIndex, boolean unlock, String nodeType);

}
