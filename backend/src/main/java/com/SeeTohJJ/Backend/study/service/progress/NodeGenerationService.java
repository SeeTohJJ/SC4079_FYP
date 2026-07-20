package com.SeeTohJJ.Backend.study.service.progress;

import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;

import java.util.List;

public interface NodeGenerationService {

    List<StudyNodePathDTO> getStudyPathNodes(Long userId);
    List<StudyNodePathDTO> getExistingNodePath(Long userId);

    void generateNewChain(Long userId);

}
