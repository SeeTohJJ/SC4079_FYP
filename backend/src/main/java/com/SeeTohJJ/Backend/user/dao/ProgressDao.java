package com.SeeTohJJ.Backend.user.dao;

import com.SeeTohJJ.Backend.user.dto.CompletedLessonResponseDTO;

import java.util.List;

public interface ProgressDao {
    int getCompletedLessons(Long userId, String topicId);
    int getTotalLessons(String topicId);
    List<CompletedLessonResponseDTO> getAllCompletedLessonsInfo(Long userId);
}
