package com.SeeTohJJ.Backend.topic.dao;

import com.SeeTohJJ.Backend.topic.model.BktParameters;

public interface TopicDao {

    String getSubTopicId(String nodeId);
    boolean existsByNodeIndex(String subtopicId, int targetNodeIndex, String nodeType);
    String getNodeId(String subtopicId, String nodeType, int targetOrderIndex);
    double getPInit(String subtopicId);
    String getTopicId(String nodeId);
    BktParameters getBktParameters(String subtopicId);
    String getTopicIdFromSubtopicId(String subtopicId);
    boolean checkSubtopicExist(String subtopicId);
    int getNodeDifficulty(String nodeId);
    String getTopicName(String topicId);
}
