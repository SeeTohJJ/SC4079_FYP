package com.SeeTohJJ.Backend.topic.service;

import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.study.dto.StudyNodeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {

    void setUserTopicInterest(Long userId, String topicId);
    boolean isTutorialCompleted(Long userId, String topicId);
    List<String> getUserTopicFromUserId(Long userId);
    String getUncompletedTutorialTopic(Long userId);
}
