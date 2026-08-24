package com.SeeTohJJ.Backend.user.dao.mastery;

import com.SeeTohJJ.Backend.user.model.UserSubTopicMastery;

import java.time.LocalDateTime;

public interface UserSubtopicMasteryDao {

    double getUserPKnow(Long userId, String subTopicId);
    void saveUserPKnow(Long userId, String subTopicId, double updatedPKnow);
    double getUserElo(Long userId, String subtopicId);
    void setUserElo(Long userId, String subtopicId, double newElo);
    void setSubtopicIsMasteredToTrue(Long userId, String subtopicId);
    void setSubtopicIsMasteredToFalse(Long userId, String subtopicId);
    boolean isSubtopicMastered(Long userId, String subtopicId);
    void insertNewSubtopicMastery(Long userId, String subtopicId, double p_know);
//    void insertUserInterestedSubtopic(Long userId, String subtopicId);
    UserSubTopicMastery getUserAttemptHistory(Long userId, String subTopicId);
    void updateCorrectAttempt(Long userId, String subTopicId, int newCorrectAttempts, int newTotalAttempts);
    void updateWrongAttempt(Long userId, String subTopicId, int newWrongAttempts, int newTotalAttempts);
    String getLowestPKnowSubtopic(Long userId);
    String getLowestPKnowSubtopicNotMastered(Long userId);
    int getCurrentChain(Long userId, String subtopicId);
    LocalDateTime getLastUpdated(Long userId, String subtopicId);
    void setUserSubtopicPKnow(Long userId, String subtopicId, double PKnow);
    void incrementHintUsage(Long userId, String subtopicId);
    boolean isTutorialCompleted(Long userId, String subtopicId);
    double getAverageEloOfTopic(Long userId, String topicId);
    double getAveragePKnowOfTopic(Long userId, String topicId);

}
