package com.SeeTohJJ.Backend.topic.dao;


import com.SeeTohJJ.Backend.topic.model.BktParameters;
import com.SeeTohJJ.Backend.topic.model.UserSubTopicMastery;

public interface UserTopicMasteryDao {

    void insertInitialTopicProgress(Long userId, String topicId);
    boolean isTutorialCompleted(Long userId, String topicId);
    String getTopUserUncompletedTopic(Long userId);

    BktParameters getBktParameters(String nodeId);
    double getUserPKnow(Long userId, String subTopicId);
    void updatePKnow(Long userId, String subTopicId, double updatedPKnow);
    UserSubTopicMastery getUserAttemptHistory(Long userId, String subTopicId);
    void updateCorrectAttempt(Long userId, String subTopicId, int newCorrectAttempts, int newTotalAttempts);
    void updateWrongAttempt(Long userId, String subTopicId, int newWrongAttempts, int newTotalAttempts);
    double getUserElo(Long userId, String subtopicId);
    void setUserNewEloRating(Long userId, String subtopicId, double newElo);
    void setIsMasteredToTrue(Long userId, String subtopicId);
    boolean isSubtopicMastered(Long userId, String subtopicId);

    String getTopicIdFromSubtopicId(String subtopicId);
    boolean checkSubtopicExist(String subtopicId);
    String getRandomUninterestedTopic(Long userId);
}
