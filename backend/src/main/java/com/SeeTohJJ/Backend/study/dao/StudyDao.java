package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
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
    QuizNodeDTO getQuizNodeContent(String nodeId);
    DecisionNodeDTO getDecisionNodeContent(String nodeId);
    EventNodeDTO getEventNodeContent(String nodeId);
    void completeNode(Long userId, String nodeId);
    String getCorrectAnswer(String nodeId);
    void saveUserQuestionAttempt(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken);
    double getQuestionRating(String nodeId);
    int getNodePositionalIndex(Long userId, String nodeId);
    void unlockNextNode(Long userId, int nodePosIndex);
    boolean checkIfNextNodeExist(Long userId, int nodePosIndex);
    String getCurrentSubtopic(Long userId);
    String getLowestPKnowSubtopic(Long userId);
    String getLowestPKnowSubtopicNotMastered(Long userId);
    int getCurrentChain(Long userId, String subtopicId);
    int getUserLastPositionIndex(Long userId);
}

