package com.SeeTohJJ.Backend.contentmanagement.service;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.CreateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.LessonRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.UpdateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.LessonResponseDTO;

import java.util.List;

public interface AdminLessonService {

    List<LessonResponseDTO> getAllActiveLessons();
    List<LessonResponseDTO> getAllInactiveLessons();

    LessonResponseDTO getLesson(String nodeId);
    LessonResponseDTO createLesson(LessonRequest request);
    LessonResponseDTO updateLesson(String nodeId, LessonRequest request);
    void setLessonInactive(String nodeId);
    void setLessonActive(String nodeId);

    int getActiveCount();
}
