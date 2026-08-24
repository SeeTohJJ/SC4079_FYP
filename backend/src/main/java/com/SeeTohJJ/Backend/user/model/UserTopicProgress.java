package com.SeeTohJJ.Backend.user.model;

import java.time.LocalDateTime;

public class UserTopicProgress {

    private Long userId;
    private String topicId;
    private int masteryScore; // 1 - 100
    private int eloRating;
    private double knowledgeProbability;
    private boolean tutorialCompleted;
    private String currentNodeId;
    private int nodesCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

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

    public int getMasteryScore() {
        return masteryScore;
    }

    public void setMasteryScore(int masteryScore) {
        this.masteryScore = masteryScore;
    }

    public int getEloRating() {
        return eloRating;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    public double getKnowledgeProbability() {
        return knowledgeProbability;
    }

    public void setKnowledgeProbability(double knowledgeProbability) {
        this.knowledgeProbability = knowledgeProbability;
    }

    public boolean isTutorialCompleted() {
        return tutorialCompleted;
    }

    public void setTutorialCompleted(boolean tutorialCompleted) {
        this.tutorialCompleted = tutorialCompleted;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public int getNodesCompleted() {
        return nodesCompleted;
    }

    public void setNodesCompleted(int nodesCompleted) {
        this.nodesCompleted = nodesCompleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
