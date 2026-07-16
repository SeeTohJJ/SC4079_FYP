package com.SeeTohJJ.Backend.topic.service;

import com.SeeTohJJ.Backend.topic.model.BktParameters;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TopicService {

    void setUserTopicInterest(Long userId, String topicId);
    boolean isTutorialCompleted(Long userId, String topicId);
    List<String> getUserTopicFromUserId(Long userId);
    String getUncompletedTutorialTopic(Long userId);
}
