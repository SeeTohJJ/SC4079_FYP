package com.SeeTohJJ.Backend.user.service;

import com.SeeTohJJ.Backend.user.dto.CompletedLessonResponseDTO;
import com.SeeTohJJ.Backend.user.dto.ProgressResponseDTO;

import java.util.List;

public interface ProgressService {
    ProgressResponseDTO getProgress(Long userId);
    List<CompletedLessonResponseDTO> getCompletedLessons(Long userId);
}
