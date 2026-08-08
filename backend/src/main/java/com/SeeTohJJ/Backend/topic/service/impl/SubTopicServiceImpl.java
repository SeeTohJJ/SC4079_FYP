package com.SeeTohJJ.Backend.topic.service.impl;

import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.study.service.progress.UserSubtopicService;
import com.SeeTohJJ.Backend.topic.dao.TopicDao;
import com.SeeTohJJ.Backend.topic.dao.UserTopicDao;
import com.SeeTohJJ.Backend.study.dao.mastery.UserTopicMasteryDao;
import com.SeeTohJJ.Backend.topic.model.BktParameters;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubTopicServiceImpl implements SubTopicService {

    private static final Logger logger = LoggerFactory.getLogger(SubTopicServiceImpl.class);

    private final TopicDao topicDao;
    private final UserTopicMasteryDao userTopicMasteryDao;
    private final UserTopicDao userTopicDao;
    private final UserStudyPathService userStudyPathService;
    private final TopicService topicService;
    private final UserSubtopicService userSubtopicService;

    @Autowired
    public SubTopicServiceImpl(TopicDao topicDao,
                               UserTopicMasteryDao userTopicMasteryDao,
                               UserTopicDao userTopicDao,
                               UserStudyPathService userStudyPathService,
                               TopicService topicService, UserSubtopicService userSubtopicService) {
        this.topicDao = topicDao;
        this.userTopicMasteryDao = userTopicMasteryDao;
        this.userTopicDao = userTopicDao;
        this.userStudyPathService = userStudyPathService;
        this.topicService = topicService;
        this.userSubtopicService = userSubtopicService;
    }

    @Override
    public String getSubTopicId(String nodeId) {
        logger.info("Starting getSubTopicId");

        return topicDao.getSubTopicId(nodeId);
    }

    @Override
    public BktParameters getBktParameters(String subtopicId) {
        logger.info("Starting getBktParameters");

        return topicDao.getBktParameters(subtopicId);
    }

    public static String incrementSubtopicId(String subtopicId){
        logger.info("Starting incrementSubtopicId");

        String prefix = subtopicId.replaceAll("\\d+$", "");
        String numberPart = subtopicId.replaceAll("\\D+", "");

        // Increment numeric part
        int nextNumber = Integer.parseInt(numberPart) + 1;

        // Preserve zero padding (same length as original number part)
        String padded = String.format("%0" + numberPart.length() + "d", nextNumber);

        return prefix + padded;
    }

    @Override
    public boolean moreLessonExists(Long userId, String subtopicId){
        logger.info("Starting moreLessonExists");

        int currentChain = userSubtopicService.getCurrentChain(userId, subtopicId);
        int targetNodeIndex = 6 + currentChain * 3; // 5 tutorial lesson + 3 standard lesson per chain + 1

        return topicDao.existsByNodeIndex(subtopicId, targetNodeIndex, "LESSON");
    }

    @Override
    public String getNodeId(String subtopicId, String nodeType, int contentSequence, int currentChain){
        logger.info("Starting getNodeId");

        int nextChain = currentChain + 1;
        int targetOrderIndex;

        // Tutorial currentChain == 1
        if (currentChain == 1){
            targetOrderIndex = contentSequence;
        }
        else{
            targetOrderIndex = (contentSequence - 1) + (nextChain * 3); // 3 standard lesson per chain
        }

        return topicDao.getNodeId(subtopicId, nodeType, targetOrderIndex);
    }

    @Override
    public double getPInit(String subtopicId){
        logger.info("Starting getPInit");

        return topicDao.getPInit(subtopicId);
    }

    @Override
    public String getNextSubtopic(String currentSubtopic){
        logger.info("Starting getNextSubtopic");

        String newSubtopic = "";

        // String replace last character to ++1
        char lastChar = currentSubtopic.charAt(currentSubtopic.length() - 1);
        if (Character.isDigit(lastChar)) {
            int nextDigit = Character.getNumericValue(lastChar) + 1;
            newSubtopic = currentSubtopic.substring(0, currentSubtopic.length() - 1) + nextDigit;
        }

        if (topicDao.checkSubtopicExist(newSubtopic)) {
            return newSubtopic;
        }

        return null;
    }

    @Override
    public int getNodeDifficulty(String nodeId) {
        logger.info("Starting getNodeDifficulty");

        return topicDao.getNodeDifficulty(nodeId);
    }

    @Override
    public String getTopicIdFromSubtopicId(String subtopicId){
        logger.info("Starting getTopicIdFromSubtopicId");

        return topicDao.getTopicIdFromSubtopicId(subtopicId);
    }



}
