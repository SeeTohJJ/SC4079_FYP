package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;

import java.util.List;

public interface StudyDao {

    List<StudyNode> getTutorialNodes(String topicId);
    List<StudyNode> generateAdaptiveNodes(Long userId, String topicId);
    List<UserNodeProgress> getExistingNodePath(Long userId);
    boolean hasActiveNodes(Long userId);
    void insertNodeIntoUserProgress(Long userId, String nodeId, String nodeType, int positionIndex, boolean isUnlocked, boolean isCompleted);
    void completeNode(Long userId, String nodeId);
    String getCorrectAnswer(String nodeId);
    int getNodePositionalIndex(Long userId, String nodeId);
    void unlockNextNode(Long userId, int nodePosIndex);
    boolean checkIfNodeExistInProgress(Long userId, int nodePosIndex);
    String getCurrentSubtopic(Long userId);
    int getUserLastPositionIndex(Long userId);
    void completeTutorialForInterestedTopic(Long userId, String subtopicId);
    List<String> getIncorrectNodes(Long userId, String subtopicId, int reviewNodeCount);
}

