package com.SeeTohJJ.Backend.garden.service;

import com.SeeTohJJ.Backend.garden.dto.GardenDTO;
import com.SeeTohJJ.Backend.garden.dto.PlantDTO;
import com.SeeTohJJ.Backend.study.model.StudyNode;

public interface GardenService {
    GardenDTO getGarden(Long userId);
    PlantDTO waterPlant(Long userId, String topicId);
    void onStudyCompleted(Long userId,String topicId, StudyNode.NodeType nodeType, boolean isCorrectAnswer);
    void createInitialPlantsDuringRegistration(Long userId);
    void createPlant(Long userId, String topicId);
}
