package com.SeeTohJJ.Backend.study.service.adaptive;

import java.time.LocalDateTime;

public interface ForgettingService {

    double applyForgetting(double pKnow, LocalDateTime lastUpdated);
    void updateForgettingDecay(Long userId, String subtopicId);
}
