package com.SeeTohJJ.Backend.user.service.mastery;

import java.time.LocalDate;

public interface UserTopicService {

    void calculateAverageElo(Long userId, String topicId);
    void calculateAveragePKnow(Long userId, String topicId);
    void updateTopicMasteryAverage(Long userId, String topicId);
    double getAverageElo(Long userId, String topicId);
    double getAveragePKnow(Long userId, String topicId);
    LocalDate getNextReviewDate(Long userId, String topicId);
    int calculateMasteryThreshold(double pKnow);
}
