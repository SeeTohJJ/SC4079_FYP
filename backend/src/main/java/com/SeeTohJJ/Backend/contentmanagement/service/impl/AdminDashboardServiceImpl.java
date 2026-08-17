package com.SeeTohJJ.Backend.contentmanagement.service.impl;

import com.SeeTohJJ.Backend.contentmanagement.dto.response.DashboardResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.*;
import com.SeeTohJJ.Backend.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardServiceImpl.class);

    private final AdminLessonService adminLessonService;
    private final AdminQuizService adminQuizService;
    private final AdminSubtopicService adminSubtopicService;
    private final AdminTopicService adminTopicService;
    private final AdminStudyChainService adminStudyChainService;
    private final UserService userService;

    public AdminDashboardServiceImpl(AdminLessonService adminLessonService,
                                     AdminQuizService adminQuizService,
                                     AdminSubtopicService adminSubtopicService,
                                     AdminTopicService adminTopicService,
                                     AdminStudyChainService adminStudyChainService,
                                     UserService userService) {
        this.adminLessonService = adminLessonService;
        this.adminQuizService = adminQuizService;
        this.adminSubtopicService = adminSubtopicService;
        this.adminTopicService = adminTopicService;
        this.adminStudyChainService = adminStudyChainService;
        this.userService = userService;
    }

    @Override
    public DashboardResponseDTO getDashboardInfo(){
        logger.info("Starting getDashboardInfo");

        int topicCount = adminTopicService.getActiveCount();
        int subtopicCount = adminSubtopicService.getActiveCount();
        int lessonCount = adminLessonService.getActiveCount();
        int quizCount = adminQuizService.getActiveCount();
        int studyChainCount = adminStudyChainService.getActiveCount();
        int userCount = userService.getActiveUserCount();
        int adminCount = userService.getActiveAdminCount();

        DashboardResponseDTO dto = new DashboardResponseDTO();

        dto.setTopicCount(topicCount);
        dto.setSubtopicCount(subtopicCount);
        dto.setLessonCount(lessonCount);
        dto.setQuizCount(quizCount);
        dto.setStudyChainCount(studyChainCount);
        dto.setUserCount(userCount);
        dto.setAdminCount(adminCount);

        return dto;
    }
}
