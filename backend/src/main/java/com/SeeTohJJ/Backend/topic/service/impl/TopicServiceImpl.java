package com.SeeTohJJ.Backend.topic.service.impl;

import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicDao;
import com.SeeTohJJ.Backend.study.dao.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final UserTopicDao userTopicDao;
    private final UserTopicMasteryDao userTopicMasteryDao;
    private final TopicDao topicDao;

    @Autowired
    public TopicServiceImpl(UserTopicDao userTopicDao,
                            UserTopicMasteryDao userTopicMasteryDao,
                            TopicDao topicDao) {
        this.userTopicDao = userTopicDao;
        this.userTopicMasteryDao = userTopicMasteryDao;
        this.topicDao = topicDao;
    }

    @Override
    public void setUserTopicInterest(Long userId, String topicId){
        logger.info("Starting setUserTopicInterest");

        userTopicDao.insertUserInterestedTopic(userId, topicId);
        userTopicMasteryDao.insertInitialTopicProgress(userId, topicId);
    }

    @Override
    public List<String> getUserTopicFromUserId(Long userId) {
        logger.info("Starting getUserTopicFromUserId");

        return userTopicDao.getUserTopicFromUserId(userId);
    }

    @Override
    public String getUncompletedTutorialTopic(Long userId){
        logger.info("Starting getUncompletedTutorial");

        return userTopicMasteryDao.getTopUserUncompletedTopic(userId);
    }

    @Override
    public String getTopicId(String nodeId) {
        logger.info("Starting getTopicId");

        return topicDao.getTopicId(nodeId);
    }

    @Override
    public String getTopicName(String topicId) {
        logger.info("Starting getTopicName");

        return topicDao.getTopicName(topicId);
    }


}
