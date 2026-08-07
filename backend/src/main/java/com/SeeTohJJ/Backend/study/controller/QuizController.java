package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.constant.EnergyConstant;
import com.SeeTohJJ.Backend.study.dto.NodeRequestDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;
import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;
import com.SeeTohJJ.Backend.study.dto.result.QuizSubmissionDTO;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.gameplay.EnergyService;
import com.SeeTohJJ.Backend.study.service.submission.QuizSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizSubmissionService quizSubmissionService;
    private final JwtService jwtService;
    private final ContentRetrievalService contentRetrievalService;
    private final EnergyService energyService;

    @Autowired
    public QuizController(QuizSubmissionService quizSubmissionService,
                          JwtService jwtService,
                          ContentRetrievalService contentRetrievalService,
                          EnergyService energyService) {
        this.quizSubmissionService = quizSubmissionService;
        this.jwtService = jwtService;
        this.contentRetrievalService = contentRetrievalService;
        this.energyService = energyService;
    }

    @PostMapping("/GetQuizContent")
    public QuizContentDTO getQuizContent(@RequestHeader("Authorization") String authHeader,
                                         @RequestBody NodeRequestDTO request) {
        logger.info("Starting getQuizContent");

        Long userId = jwtService.extractUserId(authHeader.substring(7));

        energyService.consumeEnergy(userId, EnergyConstant.ENERGY_COST_LESSON);
        return contentRetrievalService.getQuizContent(request.getNodeId());
    }

    @PostMapping("/SubmitQuiz")
    public QuizResultResponseDTO submitQuiz(@RequestHeader("Authorization") String authHeader,
                                            @RequestBody QuizSubmissionDTO request){
        logger.info("Starting submitQuiz");

        return quizSubmissionService.completeQuiz(jwtService.extractUserId(authHeader.substring(7)), request);
    }

    @GetMapping("/GetQuizHint/{nodeId}")
    public ResponseEntity<String> getQuizHint(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String nodeId) {

        logger.info("Starting getQuizHint");

        Long userId = jwtService.extractUserId(authHeader.substring(7));

        String hint = quizSubmissionService.getQuizHint(userId, nodeId);

        return ResponseEntity.ok(hint);
    }

    @GetMapping("/GetQuizExplanation/{nodeId}")
    public ResponseEntity<String> getQuizExplanation(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String nodeId
    ){
        logger.info("Starting getQuizExplanation");

        Long userId = jwtService.extractUserId(authHeader.substring(7));

        String explanation = quizSubmissionService.getQuizExplanation(nodeId);

        return ResponseEntity.ok(explanation);
    }
}
