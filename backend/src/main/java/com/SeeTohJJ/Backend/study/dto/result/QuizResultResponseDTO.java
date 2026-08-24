package com.SeeTohJJ.Backend.study.dto.result;

import java.time.LocalDate;

public class QuizResultResponseDTO {

    private boolean correct;
    private String topicName;
    private int previousMastery;
    private int updatedMastery;

    private LocalDate nextReviewDate;
    private String feedback;
    private boolean newChainGenerated;
    private int waterReward;

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

    public int getPreviousMastery() {
        return previousMastery;
    }

    public void setPreviousMastery(int previousMastery) {
        this.previousMastery = previousMastery;
    }

    public int getUpdatedMastery() {
        return updatedMastery;
    }

    public void setUpdatedMastery(int updatedMastery) {
        this.updatedMastery = updatedMastery;
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

    public int getWaterReward() {
        return waterReward;
    }

    public void setWaterReward(int waterReward) {
        this.waterReward = waterReward;
    }
}
