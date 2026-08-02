package com.SeeTohJJ.Backend.study.service.progress;

import java.time.LocalDateTime;

// Related to user subtopic mastery
public interface UserSubtopicService {

    double getUserPKnow(Long userId, String subtopicId);
    void saveUserPKnow(Long userId, String subTopicId, double updatedPKnow);
    double getUserElo(Long userId, String subtopicId);
    void setUserElo(Long userId, String subtopicId, double newElo);
    void setSubtopicIsMasteredToTrue(Long userId, String subtopicId);
    void setSubtopicIsMasteredToFalse(Long userId, String subtopicId);
    boolean isSubtopicMastered(Long userId, String subtopicId);
    void insertNewSubtopicMastery(Long userId, String subtopicId, double PKnow);
    boolean pKnowGreaterThanRating(Long userId, String subtopicId);
    void setUserSubTopicInterest(Long userId, String subtopicId);
    void updateAttemptStatistics(Long userId, String subtopicId, boolean isCorrectAnswer);
    String getUserLowestPKnowSubtopic(Long userId);
    String getUserLowestPKnowSubtopicNotMastered(Long userId);
    int getCurrentChain(Long userId, String subtopicId);
    LocalDateTime getLastUpdated(Long userId, String subtopicId);
    void setUserSubtopicPKnow(Long userId, String subtopicId, double PKnow);
    void incrementHintUsage(Long userId, String subtopicId);
    boolean isTutorialCompleted(Long userId, String topicId);
    double getAverageEloOfTopic(Long userId, String topicId);
    double getAveragePKnowOfTopic(Long userId, String topicId);

}
