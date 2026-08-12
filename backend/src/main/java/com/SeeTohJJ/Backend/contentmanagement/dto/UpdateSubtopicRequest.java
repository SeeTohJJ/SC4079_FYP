package com.SeeTohJJ.Backend.contentmanagement.dto;

public class UpdateSubtopicRequest {

    private String topicId;
    private String subtopicName;
    private int difficulty;
    private double pInit;
    private double pTransit;
    private double pSlip;
    private double pGuess;
    private boolean isActive;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getpInit() {
        return pInit;
    }

    public void setpInit(double pInit) {
        this.pInit = pInit;
    }

    public double getpTransit() {
        return pTransit;
    }

    public void setpTransit(double pTransit) {
        this.pTransit = pTransit;
    }

    public double getpSlip() {
        return pSlip;
    }

    public void setpSlip(double pSlip) {
        this.pSlip = pSlip;
    }

    public double getpGuess() {
        return pGuess;
    }

    public void setpGuess(double pGuess) {
        this.pGuess = pGuess;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
