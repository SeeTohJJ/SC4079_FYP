package com.SeeTohJJ.Backend.topic.service.impl;

import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.user.dao.UserInterestedTopicsDao;
import com.SeeTohJJ.Backend.study.dao.mastery.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final UserInterestedTopicsDao userInterestedTopicsDao;
    private final UserTopicMasteryDao userTopicMasteryDao;
    private final TopicDao topicDao;

    @Autowired
    public TopicServiceImpl(UserInterestedTopicsDao userInterestedTopicsDao,
                            UserTopicMasteryDao userTopicMasteryDao,
                            TopicDao topicDao) {
        this.userInterestedTopicsDao = userInterestedTopicsDao;
        this.userTopicMasteryDao = userTopicMasteryDao;
        this.topicDao = topicDao;
    }

    @Override
    public void insertUserTopicInterest(Long userId, String topicId){
        logger.info("Starting insertUserTopicInterest");

        userInterestedTopicsDao.insertUserInterestedTopic(userId, topicId);
        userTopicMasteryDao.insertInitialTopicProgress(userId, topicId);
    }

    @Override
    public List<String> getUserTopicFromUserId(Long userId) {
        logger.info("Starting getUserTopicFromUserId");

        return userInterestedTopicsDao.getUserTopicFromUserId(userId);
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
