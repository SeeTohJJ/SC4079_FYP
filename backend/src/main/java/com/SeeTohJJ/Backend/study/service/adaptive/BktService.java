package com.SeeTohJJ.Backend.study.service.adaptive;

public interface BktService {

    void updateUserKnowledge(Long userId, String subtopicId, boolean isCorrectAnswer, int timeTaken, double confidence);
    void updateSubTopicMastery(Long userId, String subtopicId);
}
