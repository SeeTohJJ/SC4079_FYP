package com.SeeTohJJ.Backend.user.dto;

public class TopicProgressResponseDTO {

    private String topicId;
    private String topicName;
    private int completedLessons;
    private int totalLessons;
    private double masteryScore;

    public TopicProgressResponseDTO(
            String topicId,
            String topicName,
            int completedLessons,
            int totalLessons,
            double masteryScore) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.completedLessons = completedLessons;
        this.totalLessons = totalLessons;
        this.masteryScore = masteryScore;
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

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public double getMasteryScore() {
        return masteryScore;
    }

    public void setMasteryScore(double masteryScore) {
        this.masteryScore = masteryScore;
    }
}
