package com.SeeTohJJ.Backend.topic.model;

import java.time.LocalDateTime;
import java.util.Date;

public class UserTopicMastery {

    private Long userId;
    private String topicId;
    private double averageElo;
    private double averagePKnow;
    private LocalDateTime lastUpdated;
    private Date nextReviewDate;
    private int reviewIntervalDay;
    private int reviewCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public double getAverageElo() {
        return averageElo;
    }

    public void setAverageElo(double averageElo) {
        this.averageElo = averageElo;
    }

    public double getAveragePKnow() {
        return averagePKnow;
    }

    public void setAveragePKnow(double averagePKnow) {
        this.averagePKnow = averagePKnow;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(Date nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public int getReviewIntervalDay() {
        return reviewIntervalDay;
    }

    public void setReviewIntervalDay(int reviewIntervalDay) {
        this.reviewIntervalDay = reviewIntervalDay;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }


}
