package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.dto.NodeRequestDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;
import com.SeeTohJJ.Backend.study.dto.result.QuizResultDTO;
import com.SeeTohJJ.Backend.study.service.adaptive.BktService;
import com.SeeTohJJ.Backend.study.service.adaptive.EloService;
import com.SeeTohJJ.Backend.study.service.content.ContentRetrievalService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.study.service.submission.QuizSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/study")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizSubmissionService quizSubmissionService;
    private final JwtService jwtService;
    private final ContentRetrievalService contentRetrievalService;

    @Autowired
    public QuizController(QuizSubmissionService quizSubmissionService,
                          JwtService jwtService,
                          ContentRetrievalService contentRetrievalService) {
        this.quizSubmissionService = quizSubmissionService;
        this.jwtService = jwtService;
        this.contentRetrievalService = contentRetrievalService;
    }

    @PostMapping("/GetQuizContent")
    public QuizNodeDTO getQuizContent(@RequestBody NodeRequestDTO request) {
        logger.info("Starting getQuizContent");

        return contentRetrievalService.getQuizContent(request.getNodeId());
    }

    @PostMapping("/SubmitQuiz")
    public void submitQuiz(@RequestHeader("Authorization") String authHeader,
                           @RequestBody QuizResultDTO request){
        logger.info("Starting submitQuiz");

        quizSubmissionService.completeLesson(jwtService.extractUserId(authHeader.substring(7)), request);
    }
}
