package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.study.dto.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.model.StudyNode;
import com.SeeTohJJ.Backend.study.model.UserNodeProgress;

import java.util.List;

public interface StudyDao {

    List<StudyNode> getTutorialNodes(String topicId);
    List<StudyNode> generateAdaptiveNodes(Long userId, String topicId);
    List<UserNodeProgress> getExistingNodePath(Long userId);
    boolean hasActiveNodes(Long userId);
    void insertNodeIntoUserProgress(Long userId, String nodeId, String nodeType, int positionIndex, boolean isUnlocked, boolean isCompleted);
    LessonNodeDTO getLessonNodeContent(String nodeId);
}

