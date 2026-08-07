package com.SeeTohJJ.Backend.garden.dto;

public class PlantDTO {

    private String topicId;
    private String topicName;
    private String plantType;
    private double growth;
    private double maxGrowth;
    private int happiness;
    private String stage;

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

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public double getGrowth() {
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = growth;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
