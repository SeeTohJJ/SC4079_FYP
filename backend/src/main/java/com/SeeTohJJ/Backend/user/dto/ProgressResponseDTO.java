package com.SeeTohJJ.Backend.user.dto;

import java.util.List;

public class ProgressResponseDTO {
    private int dailyStreak;
    private List<TopicProgressResponseDTO> topics;

    public ProgressResponseDTO(
            int dailyStreak,
            List<TopicProgressResponseDTO> topics) {
        this.dailyStreak = dailyStreak;
        this.topics = topics;
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
}
