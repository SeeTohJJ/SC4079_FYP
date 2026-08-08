package com.SeeTohJJ.Backend.user.service;

import com.SeeTohJJ.Backend.topic.model.Topic;

import java.util.List;

public interface UserInterestedTopicsService {

    void insertUserInterestedTopic(Long userId, String topicId);
    List<String> getUserTopicFromUserId(Long userId);
    String getRandomUninterestedTopic(Long userId);
    void completeTutorialForInterestedTopic(Long userId, String subtopicId);
    List<Topic> getInterestedTopicsByUserId(Long userId);

}
