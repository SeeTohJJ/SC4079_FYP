package com.SeeTohJJ.Backend.study.service.progress;

public interface UserTopicService {

    void calculateAverageElo(Long userId, String topicId);
    void calculateAveragePKnow(Long userId, String topicId);
    void updateTopicMasteryAverage(Long userId, String topicId);
    double getAverageElo(Long userId, String topicId);
    double getAveragePKnow(Long userId, String topicId);
}
