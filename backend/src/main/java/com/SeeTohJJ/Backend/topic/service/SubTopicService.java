package com.SeeTohJJ.Backend.topic.service;

import com.SeeTohJJ.Backend.topic.model.BktParameters;

public interface SubTopicService {

    String getSubTopicId(String nodeId);
    String getNodeId(String subtopicId, String nodeType, int contentSequence, int currentChain);
    boolean moreLessonExists(Long userId, String subtopicId);
    BktParameters getBktParameters(String subtopicId);
    double getPInit(String subtopicId);
    String getNextSubtopic(String currentSubtopic);

}
