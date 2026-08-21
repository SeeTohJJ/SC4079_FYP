package com.SeeTohJJ.Backend.user.dto;

import java.util.List;

public class ProgressResponseDTO {
    private int dailyStreak;
    private List<TopicProgressResponseDTO> topics;
    private String userName;

    public ProgressResponseDTO(int dailyStreak, List<TopicProgressResponseDTO> topics, String userName) {
        this.dailyStreak = dailyStreak;
        this.topics = topics;
        this.userName = userName;
    }

    public int getDailyStreak() {
        return dailyStreak;
    }

    public void setDailyStreak(int dailyStreak) {
        this.dailyStreak = dailyStreak;
    }

    public List<TopicProgressResponseDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicProgressResponseDTO> topics) {
        this.topics = topics;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
