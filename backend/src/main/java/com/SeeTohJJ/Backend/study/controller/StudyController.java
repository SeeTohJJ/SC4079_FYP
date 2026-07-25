package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.result.LessonResultDTO;
import com.SeeTohJJ.Backend.study.dto.*;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
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
    private final ContentRetrievalService contentRetrievalService;

    @Autowired
    public StudyController(NodeGenerationService nodeGenerationService,
                           JwtService jwtService,
                           NodeSubmissionService nodeSubmissionService,
                           ContentRetrievalService contentRetrievalService
    ) {
        this.nodeGenerationService = nodeGenerationService;
        this.jwtService = jwtService;
        this.nodeSubmissionService = nodeSubmissionService;
        this.contentRetrievalService = contentRetrievalService;
    }

    @PostMapping("/GetStudyPathNodes")
    public List<StudyNodePathDTO> getStudyPathNodes(@RequestHeader("Authorization") String authHeader) {
        logger.info("Starting getStudyPathNodes");

        return nodeGenerationService.getStudyPathNodes(jwtService.extractUserId(authHeader.substring(7)));
    }

    @PostMapping("/GetLessonContent")
    public LessonContentDTO getLessonContent(@RequestHeader("Authorization") String authHeader,
                                             @RequestBody NodeRequestDTO request) {
        logger.info("Starting getLessonContent");

        return contentRetrievalService.getLessonNodeContent(request.getNodeId());
    }

    @PostMapping("/GetDecisionContent")
    public DecisionContentDTO getDecisionContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getDecisionContent");

        return contentRetrievalService.getDecisionNodeContent(request.getNodeId());
    }

    @PostMapping("/GetEventContent")
    public EventContentDTO getEventContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getEventContent");

        return contentRetrievalService.getEventNodeContent(request.getNodeId());
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

    @PostMapping("/DecisionResult")
    public void decisionResult(){
        logger.info("Starting decisionResult");

    }

    @PostMapping("/UpdateEventResult")
    public void eventResult(){
        logger.info("Starting eventResult");

    }
}
