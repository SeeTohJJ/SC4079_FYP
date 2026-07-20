package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
import com.SeeTohJJ.Backend.study.dto.result.LessonResultDTO;
import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.submission.NodeSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study")
public class StudyController {

    private static final Logger logger = LoggerFactory.getLogger(StudyController.class);

    private final NodeGenerationService nodeGenerationService;
    private final JwtService jwtService;
    private final NodeSubmissionService nodeSubmissionService;

    @Autowired
    public StudyController(NodeGenerationService nodeGenerationService, JwtService jwtService, NodeSubmissionService nodeSubmissionService) {
        this.nodeGenerationService = nodeGenerationService;
        this.jwtService = jwtService;
        this.nodeSubmissionService = nodeSubmissionService;
    }

    @PostMapping("/GetStudyPathNodes")
    public List<StudyNodePathDTO> getStudyPathNodes(@RequestHeader("Authorization") String authHeader) {
        logger.info("Starting getStudyPathNodes");

        return nodeGenerationService.getStudyPathNodes(jwtService.extractUserId(authHeader.substring(7)));
    }

    @PostMapping("/GetLessonContent")
    public LessonNodeDTO getLessonContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getLessonContent");

        return nodeGenerationService.getLessonNodeContent(request.getNodeId());
    }

    @PostMapping("/GetQuizContent")
    public QuizNodeDTO getQuizContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getQuizContent");

        return nodeGenerationService.getQuizContent(request.getNodeId());
    }

    @PostMapping("/GetDecisionContent")
    public DecisionNodeDTO getDecisionContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getDecisionContent");

        return nodeGenerationService.getDecisionNodeContent(request.getNodeId());
    }

    @PostMapping("/GetEventContent")
    public EventNodeDTO getEventContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getEventContent");

        return nodeGenerationService.getEventNodeContent(request.getNodeId());
    }

    @PostMapping("/SubmitLesson")
    public ResponseEntity<Void> lessonResult(@RequestHeader("Authorization") String authHeader,
                                             @RequestBody LessonResultDTO request){
        logger.info("Starting lessonResult");

        nodeSubmissionService.completeLesson(
                jwtService.extractUserId(authHeader.substring(7)),
                request.getNodeId()
        );

        return ResponseEntity.ok().build();
    }

//    @PostMapping("/QuizResult")
//    public void quizResult(@RequestBody QuizResultDTO request){
//        logger.info("Starting quizResult");
//
//        studyService.completeNode(request.getUserId(), request.getNodeId());
//        scoringService.completeQuiz(request.getUserId(), request.getNodeId(), request.getIsCorrect(), request.getTimeTaken());
//    }

    @PostMapping("/DecisionResult")
    public void decisionResult(){
        logger.info("Starting decisionResult");

    }

    @PostMapping("/UpdateEventResult")
    public void eventResult(){
        logger.info("Starting eventResult");

    }
}
