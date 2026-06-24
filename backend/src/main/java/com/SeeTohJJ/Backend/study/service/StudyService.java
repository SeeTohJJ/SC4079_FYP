package com.SeeTohJJ.Backend.study.service;

import com.SeeTohJJ.Backend.study.dto.*;

import java.util.List;

public interface StudyService {

    List<StudyNodePathDTO> getStudyPathNodes(Long userId);
    List<StudyNodePathDTO> getExistingNodePath(Long userId);

    LessonNodeDTO getLessonNodeContent(String nodeId);
    QuizNodeDTO getQuizContent(String nodeId);
    DecisionNodeDTO getDecisionNodeContent(String nodeId);
    EventNodeDTO getEventNodeContent(String nodeId);
}
