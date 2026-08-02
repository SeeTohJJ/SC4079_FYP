package com.SeeTohJJ.Backend.study.dao;


import java.time.LocalDate;
import java.util.List;

public interface UserTopicMasteryDao {

    void insertInitialTopicProgress(Long userId, String topicId);
    String getTopUserUncompletedTopic(Long userId);
    void setAverageElo(Long userId, String topicId, double averageElo);
    void setAveragePKnow(Long userId, String topicId, double averagePKnow);

    void updateNextReviewDate(Long userId, String topicId, LocalDate nextReview);
    void updateIntervalDay(Long userId, String topicId, int interval);
    LocalDate getNextReview(Long userId, String topicId);
    List<String> getDueReviews(Long userId);
    double getAverageElo(Long userId, String topicId);
    double getAveragePKnow(Long userId, String topicId);
}
