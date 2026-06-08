package com.SeeTohJJ.Backend.topic.model;

import com.SeeTohJJ.Backend.auth.model.User;

import java.time.LocalDateTime;

public class UserTopicMastery {

    private Long masteryId;
    private User user;
    private Topic topic;
    private int masteryScore;
    private LocalDateTime lastUpdated;
}
