package com.SeeTohJJ.Backend.garden.service;

import com.SeeTohJJ.Backend.garden.dto.GardenDTO;
import com.SeeTohJJ.Backend.garden.dto.PlantDTO;
import com.SeeTohJJ.Backend.study.model.StudyNode;

public interface GardenService {
    GardenDTO getGarden(Long userId);
    PlantDTO waterPlant(Long userId, String topicId);
    void onNodeCompleted(Long userId,String topicId, StudyNode.NodeType nodeType, boolean isCorrectAnswer);
    void insertPlant(Long userId, String topicId);
    void insertInitialGardenTables(Long userId);
}
