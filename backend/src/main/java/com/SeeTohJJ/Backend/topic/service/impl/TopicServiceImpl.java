package com.SeeTohJJ.Backend.topic.service.impl;

import com.SeeTohJJ.Backend.topic.dao.UserTopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicMasteryDao;
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

    @Autowired
    public TopicServiceImpl(UserTopicDao userTopicDao,
                            UserTopicMasteryDao userTopicMasteryDao,
                            UserTopicMasteryDao userTopicProgressDao) {
        this.userTopicDao = userTopicDao;
        this.userTopicMasteryDao = userTopicMasteryDao;
    }

    @Override
    public void setUserTopicInterest(Long userId, String topicId){
        logger.info("Starting setUserTopicInterest");

        userTopicDao.insertUserInterestedTopic(userId, topicId);
        userTopicMasteryDao.insertInitialTopicProgress(userId, topicId);
    }

    @Override
    public boolean isTutorialCompleted(Long userId, String topicId){
        logger.info("Starting isTutorialCompleted");

        return userTopicMasteryDao.isTutorialCompleted(userId, topicId);
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


}
