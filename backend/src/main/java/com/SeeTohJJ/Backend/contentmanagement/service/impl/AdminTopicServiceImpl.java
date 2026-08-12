package com.SeeTohJJ.Backend.contentmanagement.service.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.CreateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.UpdateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminTopicService;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.dto.TopicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTopicServiceImpl implements AdminTopicService {
    private static final Logger logger = LoggerFactory.getLogger(AdminTopicServiceImpl.class);

    private final TopicDao topicDao;

    public AdminTopicServiceImpl(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    public List<TopicDTO> getAllTopics() {
        logger.info("Start getAllTopics");

        return topicDao.getAllTopics();
    }

    @Override
    public TopicDTO getTopic(String topicId) {
        logger.info("Starting getTopic");

        return topicDao.findById(topicId);
    }

    @Override
    public TopicDTO createTopic(CreateTopicRequest request) {
        logger.info("Starting createTopic");

        String topicName = request.getTopicName();
        String topicDescription = request.getTopicDescription();

        validateTopic(topicName, topicDescription);

        if (topicDao.existsByName(topicName)) {
            throw new IllegalArgumentException("A topic with this name already exists");
        }

        String topicId = topicDao.findNextTopicId();
        topicDao.create(topicId, topicName, topicDescription);

        return topicDao.findById(topicId);
    }

    @Override
    public TopicDTO updateTopic(String topicId, UpdateTopicRequest request) {
        logger.info("Starting updateTopic");

        String topicName = request.getTopicName();
        String topicDescription = request.getTopicDescription();

        validateTopic(topicName, topicDescription);

        topicDao.update(topicId, topicName, topicDescription);

        return null;
    }

    private void validateTopic(String topicName, String topicDescription) {
        logger.info("Starting validateTopic");

        if (topicName == null || topicName.trim().isEmpty()) {
            throw new IllegalArgumentException("Topic name cannot be empty");
        }

        if (topicDescription != null) {
            topicDescription = topicDescription.trim();
        }

        if (topicName.length() > 255) {
            throw new IllegalArgumentException("Topic name cannot exceed 255 characters");
        }

        if (topicDescription != null && topicDescription.length() > 255) {
            throw new IllegalArgumentException("Topic description cannot exceed 255 characters");
        }
    }

    @Override
    public void setTopicInactive(String topicId) {
        logger.info("Starting setTopicInactive");

        topicDao.setTopicInactive(topicId);
    }

    @Override
    public void setTopicActive(String topicId) {
        logger.info("Starting setTopicActive");

        topicDao.setTopicActive(topicId);
    }
}