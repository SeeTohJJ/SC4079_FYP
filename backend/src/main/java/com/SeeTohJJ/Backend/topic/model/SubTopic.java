package com.SeeTohJJ.Backend.topic.model;

import java.time.LocalDateTime;

public class SubTopic {

    private String subTopicId;
    private String topicId;
    private String subTopicName;
    private int difficulty;

    private double p_init;
    private double p_transit;
    private double p_slip;
    private double p_guess;

    private LocalDateTime lastUpdated;

    public String getSubTopicId() {
        return subTopicId;
    }

    public void setSubTopicId(String subTopicId) {
        this.subTopicId = subTopicId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public double getP_init() {
        return p_init;
    }

    public void setP_init(double p_init) {
        this.p_init = p_init;
    }

    public double getP_transit() {
        return p_transit;
    }

    public void setP_transit(double p_transit) {
        this.p_transit = p_transit;
    }

    public double getP_slip() {
        return p_slip;
    }

    public void setP_slip(double p_slip) {
        this.p_slip = p_slip;
    }

    public double getP_guess() {
        return p_guess;
    }

    public void setP_guess(double p_guess) {
        this.p_guess = p_guess;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
