package com.SeeTohJJ.Backend.topic.model;

import com.SeeTohJJ.Backend.auth.model.User;

public class UserInterestedTopic {

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    private Long userTopicId;
    private User user;
    private Topic topic;
    private Status status;

    public UserInterestedTopic() {
        this.status = Status.ACTIVE;
    }

    public Long getUserTopicId() {
        return userTopicId;
    }
    public void setUserTopicId(Long userTopicId) {
        this.userTopicId = userTopicId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
