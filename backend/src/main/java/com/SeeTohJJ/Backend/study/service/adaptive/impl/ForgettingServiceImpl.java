package com.SeeTohJJ.Backend.study.service.adaptive.impl;

import com.SeeTohJJ.Backend.study.service.adaptive.ForgettingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ForgettingServiceImpl implements ForgettingService {

    @Override
    public double applyForgetting(double currentPKnow, LocalDateTime lastUpdated) {

        long daysSinceReview = ChronoUnit.DAYS.between(lastUpdated, LocalDateTime.now());

        double lambda = getForgettingRate(currentPKnow); // forgetting rate

        return currentPKnow * Math.exp(-lambda * daysSinceReview);
    }

    private double getForgettingRate(double pKnow) {
        if (pKnow >= 0.9) return 0.01;   // mastered concepts decay slowly
        if (pKnow >= 0.7) return 0.02;
        return 0.04;                     // weak concepts decay faster
    }
}
