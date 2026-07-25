package com.SeeTohJJ.Backend.study.service.progress.impl;

import com.SeeTohJJ.Backend.study.dao.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.study.service.progress.UserTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTopicServiceImpl implements UserTopicService {

    private static final Logger logger = LoggerFactory.getLogger(UserTopicServiceImpl.class);

    private final UserTopicMasteryDao userTopicMasteryDao;

    @Autowired
    public UserTopicServiceImpl(UserTopicMasteryDao userTopicMasteryDao) {
        this.userTopicMasteryDao = userTopicMasteryDao;
    }

    @Override
    public boolean isTutorialCompleted(Long userId, String topicId){
        logger.info("Starting isTutorialCompleted");

        return userTopicMasteryDao.isTutorialCompleted(userId, topicId);
    }
}
