package com.SeeTohJJ.Backend.study.service.adaptive;

public interface BktService {

    void runBktModel(Long userId, String subtopicId, boolean isCorrectAnswer, int timeTaken, double confidence);
    void updateUserKnowledge(Long userId, String subtopicId, boolean isCorrectAnswer, int timeTaken, double confidence);
    void updateSubTopicMastery(Long userId, String subtopicId);
}
