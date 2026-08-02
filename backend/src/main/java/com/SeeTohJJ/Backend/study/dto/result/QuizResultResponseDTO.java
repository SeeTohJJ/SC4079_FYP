package com.SeeTohJJ.Backend.study.dto.result;

import java.time.LocalDate;

public class QuizResultResponseDTO {

    private boolean correct;
    private String topicName;
    private double previousPKnow;
    private double updatedPKnow;

    private LocalDate nextReviewDate;
    private String feedback;
    private boolean newChainGenerated;

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public double getPreviousPKnow() {
        return previousPKnow;
    }

    public void setPreviousPKnow(double previousPKnow) {
        this.previousPKnow = previousPKnow;
    }

    public double getUpdatedPKnow() {
        return updatedPKnow;
    }

    public void setUpdatedPKnow(double updatedPKnow) {
        this.updatedPKnow = updatedPKnow;
    }

    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(LocalDate nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isNewChainGenerated() {
        return newChainGenerated;
    }

    public void setNewChainGenerated(boolean newChainGenerated) {
        this.newChainGenerated = newChainGenerated;
    }
}
