package com.SeeTohJJ.Backend.user.dao;

public interface ProgressDao {
    int getCompletedLessons(Long userId, String topicId);
    int getTotalLessons(String topicId);
}
