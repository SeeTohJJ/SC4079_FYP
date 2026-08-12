package com.SeeTohJJ.Backend.contentmanagement.service;

import com.SeeTohJJ.Backend.contentmanagement.dto.CreateSubtopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.UpdateSubtopicRequest;
import com.SeeTohJJ.Backend.topic.dto.SubtopicDTO;
import java.util.List;

public interface AdminSubtopicService {

    List<SubtopicDTO> getAllSubtopics();

    SubtopicDTO getSubtopic(String subtopicId);
    SubtopicDTO createSubtopic(CreateSubtopicRequest request);
    SubtopicDTO updateSubtopic(String subtopicId, UpdateSubtopicRequest request);
    void setSubtopicInactive(String subtopicId);
    void setSubtopicActive(String subtopicId);

}
