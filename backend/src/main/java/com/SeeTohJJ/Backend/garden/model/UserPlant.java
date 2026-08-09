package com.SeeTohJJ.Backend.garden.model;

import java.time.LocalDateTime;

public class UserPlant {

    private Long userId;
    private String topicId;
    private String topicName;
    private double currentGrowth;
    private double maxGrowth;
    private int happiness;
    private LocalDateTime lastWatered;
    private LocalDateTime lastGrowthUpdate;
    private String stage;

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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    
    public double getCurrentGrowth() {
        return currentGrowth;
    }

    public void setCurrentGrowth(double currentGrowth) {
        this.currentGrowth = currentGrowth;
    }

    public double getMaxGrowth() {
        return maxGrowth;
    }

    public void setMaxGrowth(double maxGrowth) {
        this.maxGrowth = maxGrowth;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public LocalDateTime getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDateTime lastWatered) {
        this.lastWatered = lastWatered;
    }

    public LocalDateTime getLastGrowthUpdate() {
        return lastGrowthUpdate;
    }

    public void setLastGrowthUpdate(LocalDateTime lastGrowthUpdate) {
        this.lastGrowthUpdate = lastGrowthUpdate;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
