package com.SeeTohJJ.Backend.contentmanagement.service;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.CreateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.UpdateTopicRequest;
import com.SeeTohJJ.Backend.topic.dto.TopicDTO;

import java.util.List;

public interface AdminTopicService {

    List<TopicDTO> getAllTopics();
    TopicDTO getTopic(String topicId);
    TopicDTO createTopic(CreateTopicRequest request);
    TopicDTO updateTopic(String topicId, UpdateTopicRequest request);
    void setTopicInactive(String topicId);
    void setTopicActive(String topicId);

}
