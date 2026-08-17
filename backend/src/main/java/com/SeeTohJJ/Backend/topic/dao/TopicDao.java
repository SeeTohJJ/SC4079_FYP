package com.SeeTohJJ.Backend.topic.dao;

import com.SeeTohJJ.Backend.topic.dto.TopicDTO;
import java.util.List;

public interface TopicDao {

    String getTopicId(String nodeId);
    String getTopicName(String topicId);

    List<TopicDTO> getAllTopics();
    TopicDTO findById(String topicId);
    void setTopicInactive(String topicId);
    void setTopicActive(String topicId);
    boolean existsByName(String topicName);
    String findNextTopicId();
    void create(String topicId, String topicName, String topicDescription);
    void update(String topicId, String topicName, String topicDescription);
    int getActiveCount();
}
