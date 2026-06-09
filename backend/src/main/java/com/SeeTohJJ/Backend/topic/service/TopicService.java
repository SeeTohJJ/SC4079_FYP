package com.SeeTohJJ.Backend.topic.service;

import com.SeeTohJJ.Backend.auth.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public interface TopicService {

    void setUserTopicInterest(Long userId, String topicId);

}
