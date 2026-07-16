package com.SeeTohJJ.Backend.topic.dao;

import com.SeeTohJJ.Backend.topic.model.BktParameters;

public interface TopicDao {

    String getSubTopicId(String topicId);
    boolean existsByNodeIndex(String subtopicId, int targetNodeIndex, String nodeType);
    String getNodeId(String subtopicId, String nodeType, int targetOrderIndex);

    }
