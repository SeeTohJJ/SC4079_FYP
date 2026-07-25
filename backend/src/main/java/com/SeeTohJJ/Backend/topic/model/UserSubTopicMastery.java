package com.SeeTohJJ.Backend.topic.model;

import java.time.LocalDateTime;

public class UserSubTopicMastery {

    private Long userId;
    private String subtopicId;
    private double pKnow;
    private double elo;
    private int attemptCount;
    private int correctCount;
    private int wrongCount;
    private int reviewCount;
    private LocalDateTime lastUpdated;
    private int currentChain;
    private int hintUsed;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSubtopicId() {
        return subtopicId;
    }

    public void setSubtopicId(String subtopicId) {
        this.subtopicId = subtopicId;
    }

    public double getPKnow() {
        return pKnow;
    }

    public void setPKnow(double pKnow) {
        this.pKnow = pKnow;
    }

    public double getElo() {
        return elo;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getCurrentChain() {
        return currentChain;
    }

    public void setCurrentChain(int currentChain) {
        this.currentChain = currentChain;
    }

    public int getHintUsed() {
        return hintUsed;
    }

    public void setHintUsed(int hintUsed) {
        this.hintUsed = hintUsed;
    }
}
