package com.SeeTohJJ.Backend.study.service.adaptive;

public interface AttemptHistoryService {

    void markHintUsed(Long userId, String nodeId);
    void saveQuestionAttemptHistory(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken, boolean hintUsed);
    int getQuizAttemptHistoryCount(Long userId, String nodeId);
}
