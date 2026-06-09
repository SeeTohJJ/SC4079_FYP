package com.SeeTohJJ.Backend.topic.model;

import java.time.LocalDateTime;

public class UserTopicMastery {

    private String masteryId;
    private String userId;
    private String topicId;
    private int masteryScore; // 1 - 100
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public String getMasteryId() {
        return masteryId;
    }

    public void setMasteryId(String masteryId) {
        this.masteryId = masteryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getMasteryScore() {
        return masteryScore;
    }

    public void setMasteryScore(int masteryScore) {
        this.masteryScore = masteryScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
