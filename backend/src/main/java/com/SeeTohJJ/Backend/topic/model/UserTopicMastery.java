package com.SeeTohJJ.Backend.topic.model;

import com.SeeTohJJ.Backend.auth.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_topic_mastery")
public class UserTopicMastery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masteryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private int masteryScore;

    private LocalDateTime lastUpdated;
}
