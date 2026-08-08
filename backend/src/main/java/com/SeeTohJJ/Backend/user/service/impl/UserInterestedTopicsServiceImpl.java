package com.SeeTohJJ.Backend.user.service.impl;

import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.user.dao.UserInterestedTopicsDao;
import com.SeeTohJJ.Backend.user.service.UserInterestedTopicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInterestedTopicsServiceImpl implements UserInterestedTopicsService {

    private static final Logger logger = LoggerFactory.getLogger(UserInterestedTopicsServiceImpl.class);

    private final UserInterestedTopicsDao userInterestedTopicsDao;

    @Autowired
    public UserInterestedTopicsServiceImpl(UserInterestedTopicsDao userInterestedTopicsDao) {
        this.userInterestedTopicsDao = userInterestedTopicsDao;
    }

    @Override
    public void insertUserInterestedTopic(Long userId, String topicId){
        logger.info("Starting insertUserInterestedTopic");

        userInterestedTopicsDao.insertUserInterestedTopic(userId, topicId);
    }

    @Override
    public List<String> getUserTopicFromUserId(Long userId){
        logger.info("Starting getUserTopicFromUserId");

        return userInterestedTopicsDao.getUserTopicFromUserId(userId);
    }

    @Override
    public void completeTutorialForInterestedTopic(Long userId, String subtopicId) {
        logger.info("Starting completeTutorialForInterestedTopic");

        userInterestedTopicsDao.completeTutorialForInterestedTopic(userId, subtopicId);
    }

    @Override
    public String getRandomUninterestedTopic(Long userId){
        logger.info("Starting getRandomUninterestedTopic");

        return userInterestedTopicsDao.getRandomUninterestedTopic(userId);
    }

    @Override
    public List<Topic> getInterestedTopicsByUserId(Long userId) {
        logger.info("Starting getInterestedTopicsByUserId");

        return userInterestedTopicsDao.getInterestedTopicsByUserId(userId);
    }



}
