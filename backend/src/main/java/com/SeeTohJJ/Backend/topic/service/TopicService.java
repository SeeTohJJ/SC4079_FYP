package com.SeeTohJJ.Backend.topic.service;

import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.topic.model.UserInterestedTopic;
import com.SeeTohJJ.Backend.topic.repository.UserTopicRepository;
import com.SeeTohJJ.Backend.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    UserTopicRepository userTopicRepository;

    public void setUserTopicInterest(User user, Topic topic) {
        logger.info("Starting setUserTopicInterest");

        if (userTopicRepository.existsByUserAndTopic(user, topic)) {
            throw new RuntimeException("Topic already exists");
        }

        UserInterestedTopic userInterestedTopic =
                new UserInterestedTopic();

        userInterestedTopic.setUser(user);
        userInterestedTopic.setTopic(topic);
        userInterestedTopic.setStatus(UserInterestedTopic.Status.ACTIVE);

        userTopicRepository.save(userInterestedTopic);
    }


}
