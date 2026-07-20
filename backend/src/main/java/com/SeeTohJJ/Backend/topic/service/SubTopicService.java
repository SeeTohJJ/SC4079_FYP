package com.SeeTohJJ.Backend.topic.service;

import com.SeeTohJJ.Backend.topic.model.BktParameters;

public interface SubTopicService {

    String getSubTopicId(String nodeId);
    BktParameters getBktParameters(String subtopicId);
    double getUserPKnow(Long userId, String subtopicId);
    void updatePKnow(Long userId, String subtopicId, double updatedPKnow);
    void updateAttemptStatistics(Long userId, String subtopicId, boolean isCorrectAnswer);
    double getUserElo(Long userId, String subtopicId);
    void setUserNewEloRating(Long userId, String subtopicId, double newElo);
    boolean pKnowGreaterThanRating(Long userId, String subtopicId);
    void updateSubTopicMastery(Long userId, String subtopicId);
    boolean isSubtopicMastered(Long userId, String subtopicId);
    String createNewUserInterestedSubtopic(Long userId, String subtopicId);
    void setUserSubTopicInterest(Long userId, String subtopicId);
    boolean moreLessonExists(Long userId, String subtopicId);

    String getNodeId(String subtopicId, String nodeType, int contentSequence, int currentChain);
    void insertNewSubtopicMastery(Long userId, String subtopicId);
}
