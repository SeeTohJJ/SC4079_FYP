package com.SeeTohJJ.Backend.topic.model;

import java.time.LocalDateTime;

public class UserInterestedTopic {

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    private Long userTopicId;
    private String userId;
    private String topicId;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public Long getUserTopicId() {
        return userTopicId;
    }

    public void setUserTopicId(Long userTopicId) {
        this.userTopicId = userTopicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
