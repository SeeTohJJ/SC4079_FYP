package com.SeeTohJJ.Backend.contentmanagement.service.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.LessonRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.LessonResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminLessonService;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminQuizService;
import com.SeeTohJJ.Backend.study.dao.NodeContentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminLessonServiceImpl implements AdminLessonService {

    private static final Logger logger = LoggerFactory.getLogger(AdminLessonServiceImpl.class);
    private final NodeContentDao nodeContentDao;

    @Autowired
    public AdminLessonServiceImpl(NodeContentDao nodeContentDao) {
        this.nodeContentDao = nodeContentDao;
    }

    @Override
    public List<LessonResponseDTO> getAllLessons(){
        logger.info("Starting getAllLessons");

        return nodeContentDao.getAllLessons();
    }

    @Override
    public LessonResponseDTO getLesson(String nodeId){
        logger.info("Starting getLesson");

        return nodeContentDao.getLesson(nodeId);
    }

    @Override
    public LessonResponseDTO createLesson(LessonRequest request){
        logger.info("Starting createLesson");

        String nodeId =  request.getNodeId();
        String topicId = request.getTopicId();
        String subtopicId = request.getSubtopicId();
        String title = request.getTitle();
        int orderIndex = request.getOrderIndex();
        int requiredMastery = request.getRequiredMastery();
        String content = request.getContent();

        nodeContentDao.createLesson(nodeId, topicId, subtopicId, title, orderIndex, requiredMastery, content);

        return nodeContentDao.getLesson(nodeId);
    }

    @Override
    public LessonResponseDTO updateLesson(String nodeId, LessonRequest request){
        logger.info("Starting updateLesson");

        String topicId = request.getTopicId();
        String subtopicId = request.getSubtopicId();
        String title = request.getTitle();
        int orderIndex = request.getOrderIndex();
        int requiredMastery = request.getRequiredMastery();
        String content = request.getContent();

        nodeContentDao.updateLesson(nodeId, topicId, subtopicId, title, orderIndex, requiredMastery, content);

        return nodeContentDao.getLesson(nodeId);
    }

    @Override
    public void setLessonInactive(String nodeId){
        logger.info("Starting setLessonInactive");

        nodeContentDao.setLessonInactive(nodeId);
    }

    @Override
    public void setLessonActive(String nodeId){
        logger.info("Starting setLessonActive");

        nodeContentDao.setLessonActive(nodeId);
    }

}
