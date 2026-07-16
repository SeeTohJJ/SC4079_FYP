package com.SeeTohJJ.Backend.study.controller;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultDTO;
import com.SeeTohJJ.Backend.study.service.adaptive.BktService;
import com.SeeTohJJ.Backend.study.service.adaptive.EloService;
import com.SeeTohJJ.Backend.study.service.progress.NodeGenerationService;
import com.SeeTohJJ.Backend.study.service.progress.ProgressService;
import com.SeeTohJJ.Backend.study.service.submission.QuizSubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizSubmissionService quizSubmissionService;

    @Autowired
    public QuizController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @PostMapping("/submit/review")
    public void submitReviewQuiz(@RequestBody QuizResultDTO request){
        logger.info("Starting submitReviewQuiz");

        quizSubmissionService.submitReviewQuiz(request);
    }
}
