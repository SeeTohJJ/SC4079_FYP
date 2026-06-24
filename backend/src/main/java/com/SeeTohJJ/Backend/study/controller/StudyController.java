package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study")
public class StudyController {

    private static final Logger logger = LoggerFactory.getLogger(StudyController.class);

    private final StudyService studyService;
    private final JwtService jwtService;

    @Autowired
    public StudyController(StudyService studyService, JwtService jwtService) {
        this.studyService = studyService;
        this.jwtService = jwtService;
    }

    @PostMapping("/GetStudyPathNodes")
    public List<StudyNodePathDTO> GetStudyPathNodes(@RequestBody StudyRequestDTO request) {
        logger.info("Starting GetStudyPathNodes");

        Long userId = jwtService.extractUserId(request.getToken());

        return studyService.getStudyPathNodes(userId);
    }

    @PostMapping("/GetLessonContent")
    public LessonNodeDTO getLessonContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getLessonContent");

        return studyService.getLessonNodeContent(request.getNodeId());
    }

    @PostMapping("GetQuizContent")
    public QuizNodeDTO getQuizContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getQuizContent");

        return studyService.getQuizContent(request.getNodeId());
    }

    @PostMapping("GetDecisionContent")
    public DecisionNodeDTO getDecisionContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getDecisionContent");

        return studyService.getDecisionNodeContent(request.getNodeId());
    }

    @PostMapping("GetEventContent")
    public EventNodeDTO getEventContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getEventContent");

        return studyService.getEventNodeContent(request.getNodeId());
    }
}
