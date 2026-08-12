package com.SeeTohJJ.Backend.topic.dao;

import com.SeeTohJJ.Backend.topic.dto.SubtopicDTO;
import com.SeeTohJJ.Backend.topic.dto.TopicDTO;
import com.SeeTohJJ.Backend.topic.model.BktParameters;

import java.util.List;

public interface SubtopicDao {

    String getSubTopicId(String nodeId);
    boolean existsByNodeIndex(String subtopicId, int targetNodeIndex, String nodeType);
    int getNodeDifficulty(String nodeId);
    String getNodeId(String subtopicId, String nodeType, int targetOrderIndex);
    double getPInit(String subtopicId);
    BktParameters getBktParameters(String subtopicId);
    String getTopicIdFromSubtopicId(String subtopicId);
    boolean checkSubtopicExist(String subtopicId);

    List<SubtopicDTO> getAllSubtopics();
    SubtopicDTO findById(String subtopicId);
    void setSubtopicInactive(String subtopicId);
    void setSubtopicActive(String subtopicId);
    boolean existsByName(String subtopicName);
    String findNextSubtopicId(String topicId);
    void create(String subtopicId, String topicId, String subtopicName, int difficulty,
                double pInit, double pTransit, double pSlip, double pGuess);
    void update(String subtopicId, String topicId, String subtopicName, int difficulty,
                double pInit, double pTransit, double pSlip, double pGuess);
}
