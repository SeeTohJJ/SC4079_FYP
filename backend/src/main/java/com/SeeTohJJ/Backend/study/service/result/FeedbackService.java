package com.SeeTohJJ.Backend.study.service.result;

public interface FeedbackService {

    String generateFeedback(boolean isCorrect, double updatedPKnow, int timeTaken);
}
