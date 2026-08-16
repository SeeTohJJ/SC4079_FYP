package com.SeeTohJJ.Backend.topic.dto;

public class SubtopicDTO {

    private String subtopicId;
    private String topicId;
    private String subtopicName;
    private int difficulty;
    private Double pInit;
    private Double pTransit;
    private Double pSlip;
    private Double pGuess;
    private boolean isActive;

    public String getSubtopicId() {
        return subtopicId;
    }

    public void setSubtopicId(String subtopicId) {
        this.subtopicId = subtopicId;
    }

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

    public Double getPInit() {
        return pInit;
    }

    public void setPInit(Double pInit) {
        this.pInit = pInit;
    }

    public Double getPTransit() {
        return pTransit;
    }

    public void setPTransit(Double pTransit) {
        this.pTransit = pTransit;
    }

    public Double getPSlip() {
        return pSlip;
    }

    public void setPSlip(Double pSlip) {
        this.pSlip = pSlip;
    }

    public Double getPGuess() {
        return pGuess;
    }

    public void setPGuess(Double pGuess) {
        this.pGuess = pGuess;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
