package com.SeeTohJJ.Backend.topic.model;

import com.SeeTohJJ.Backend.user.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_interested_topics")
public class UserInterestedTopic {

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTopicId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Enumerated(EnumType.STRING)
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
