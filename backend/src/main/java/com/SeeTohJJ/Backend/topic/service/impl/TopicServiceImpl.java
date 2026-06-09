package com.SeeTohJJ.Backend.topic.service.impl;

import com.SeeTohJJ.Backend.auth.model.User;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicDao topicDao;
    private final UserTopicDao userTopicDao;
    private final UserTopicMasteryDao userTopicMasteryDao;

    @Autowired
    public TopicServiceImpl(TopicDao topicDao, UserTopicDao userTopicDao, UserTopicMasteryDao userTopicMasteryDao) {
        this.topicDao = topicDao;
        this.userTopicDao = userTopicDao;
        this.userTopicMasteryDao = userTopicMasteryDao;
    }

    @Override
    public void setUserTopicInterest(Long userId, String topicId){
        logger.info("Starting setUserTopicInterest");

        userTopicDao.insertUserInterestedTopic(userId, topicId);
        userTopicMasteryDao.insertInitialTopicMastery(userId, topicId);
    }

}
