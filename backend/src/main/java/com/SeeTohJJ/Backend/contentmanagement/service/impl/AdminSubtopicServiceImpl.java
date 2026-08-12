package com.SeeTohJJ.Backend.contentmanagement.service.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.CreateSubtopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.UpdateSubtopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminSubtopicService;
import com.SeeTohJJ.Backend.topic.dao.SubtopicDao;
import com.SeeTohJJ.Backend.topic.dto.SubtopicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSubtopicServiceImpl implements AdminSubtopicService {
    private static final Logger logger = LoggerFactory.getLogger(AdminSubtopicServiceImpl.class);

    private final SubtopicDao subtopicDao;

    public AdminSubtopicServiceImpl(SubtopicDao subtopicDao) {
        this.subtopicDao = subtopicDao;
    }

    @Override
    public List<SubtopicDTO> getAllSubtopics() {
        logger.info("Start getAllSubtopics");

        return subtopicDao.getAllSubtopics();
    }

    @Override
    public SubtopicDTO getSubtopic(String subtopicId) {
        logger.info("Starting getSubtopic");

        return subtopicDao.findById(subtopicId);
    }

    @Override
    public SubtopicDTO createSubtopic(CreateSubtopicRequest request) {
        logger.info("Starting createSubtopic");

        String subtopicName = request.getSubtopicName();
        String topicId = request.getTopicId();
        int difficulty = request.getDifficulty();
        double pInit = request.getpInit();
        double pTransit = request.getpTransit();
        double pSlip = request.getpSlip();
        double pGuess = request.getpGuess();

        validateSubtopic(subtopicName);

        if (subtopicDao.existsByName(subtopicName)) {
            throw new IllegalArgumentException("A subtopic with this name already exists");
        }

        String subtopicId = subtopicDao.findNextSubtopicId(topicId);
        subtopicDao.create(subtopicId, topicId, subtopicName, difficulty, pInit, pTransit, pSlip, pGuess);

        return subtopicDao.findById(subtopicId);
    }

    @Override
    public SubtopicDTO updateSubtopic(String subtopicId, UpdateSubtopicRequest request) {
        logger.info("Starting updateSubtopic");

        String subtopicName = request.getSubtopicName();
        String topicId = request.getTopicId();
        int difficulty = request.getDifficulty();
        double pInit = request.getpInit();
        double pTransit = request.getpTransit();
        double pSlip = request.getpSlip();
        double pGuess = request.getpGuess();
        validateSubtopic(subtopicName);

        subtopicDao.update(subtopicId, topicId, subtopicName, difficulty, pInit, pTransit, pSlip, pGuess);

        return null;
    }

    private void validateSubtopic(String subtopicName) {
        logger.info("Starting validateSubtopic");

        if (subtopicName == null || subtopicName.trim().isEmpty()) {
            throw new IllegalArgumentException("Subtopic name cannot be empty");
        }

        if (subtopicName.length() > 50) {
            throw new IllegalArgumentException("Subtopic name cannot exceed 50 characters");
        }

    }

    @Override
    public void setSubtopicInactive(String subtopicId) {
        logger.info("Starting setSubtopicInactive");

        subtopicDao.setSubtopicInactive(subtopicId);
    }

    @Override
    public void setSubtopicActive(String subtopicId) {
        logger.info("Starting setSubtopicActive");

        subtopicDao.setSubtopicActive(subtopicId);
    }
}