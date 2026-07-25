package com.SeeTohJJ.Backend.study.dao;

public interface AttemptHistoryDao {
    void saveUserQuestionAttempt(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken, boolean hintUsed);
    int getQuizAttemptHistoryCount(Long userId, String nodeId);
}
