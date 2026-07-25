package com.SeeTohJJ.Backend.study.service.progress;

import com.SeeTohJJ.Backend.study.dto.*;

import java.util.List;

public interface NodeGenerationService {

    List<StudyNodePathDTO> getStudyPathNodes(Long userId);
    List<StudyNodePathDTO> getExistingNodePath(Long userId);

    void generateNewChain(Long userId);

}
