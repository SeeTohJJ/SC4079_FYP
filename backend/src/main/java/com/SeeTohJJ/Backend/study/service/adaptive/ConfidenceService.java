package com.SeeTohJJ.Backend.study.service.adaptive;

public interface ConfidenceService {

    double getConfidence(Long userId, String nodeId, int timeTaken, boolean hintUsed);
}
