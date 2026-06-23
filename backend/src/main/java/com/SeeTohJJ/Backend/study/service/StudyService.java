package com.SeeTohJJ.Backend.study.service;

import com.SeeTohJJ.Backend.study.dto.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.StudyNodeDTO;
import com.SeeTohJJ.Backend.study.dto.StudyNodePathDTO;

import java.util.List;

public interface StudyService {

    List<StudyNodePathDTO> getStudyPathNodes(Long userId);
    List<StudyNodePathDTO> getExistingNodePath(Long userId);

    LessonNodeDTO getLessonNodeContent(String nodeId);
}
