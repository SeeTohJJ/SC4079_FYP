package com.SeeTohJJ.Backend.user.service.impl;

import com.SeeTohJJ.Backend.study.service.progress.UserTopicService;
import com.SeeTohJJ.Backend.topic.model.Topic;
import com.SeeTohJJ.Backend.user.dao.ProgressDao;
import com.SeeTohJJ.Backend.user.dto.ProgressResponseDTO;
import com.SeeTohJJ.Backend.user.dto.TopicProgressResponseDTO;
import com.SeeTohJJ.Backend.user.service.LoginStreakService;
import com.SeeTohJJ.Backend.user.service.ProgressService;
import com.SeeTohJJ.Backend.user.service.UserInterestedTopicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final LoginStreakService loginStreakService;
    private final ProgressDao progressDao;
    private final UserInterestedTopicsService userInterestedTopicsService;
    private final UserTopicService userTopicService;

    @Autowired
    public ProgressServiceImpl(LoginStreakService loginStreakService,
                               ProgressDao progressDao,
                               UserInterestedTopicsService userInterestedTopicsService, UserTopicService userTopicService) {
        this.loginStreakService = loginStreakService;
        this.progressDao = progressDao;
        this.userInterestedTopicsService = userInterestedTopicsService;
        this.userTopicService = userTopicService;
    }

    @Override
    public ProgressResponseDTO getProgress(Long userId) {

        int streak = loginStreakService.getCurrentStreak(userId);

        List<Topic> topics = userInterestedTopicsService.getInterestedTopicsByUserId(userId);

        List<TopicProgressResponseDTO> topicProgress = new ArrayList<>();

        for (Topic topic : topics) {
            String topicId = topic.getTopicId();

            int completedLessons = progressDao.getCompletedLessons(userId, topicId);
            int totalLessons = progressDao.getTotalLessons(topicId);
            double pKnow = userTopicService.getAveragePKnow(userId, topicId);

            topicProgress.add(
                    new TopicProgressResponseDTO(
                            topicId,
                            topic.getName(),
                            completedLessons,
                            totalLessons,
                            pKnow
                    )
            );
        }

        return new ProgressResponseDTO(streak, topicProgress);
    }
}
