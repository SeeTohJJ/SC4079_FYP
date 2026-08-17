package com.SeeTohJJ.Backend.contentmanagement.controller;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.QuizRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.QuizResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminQuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/quizzes")
public class AdminQuizController {

    private static final Logger logger = LoggerFactory.getLogger(AdminQuizController.class);

    private final AdminQuizService adminQuizService;

    public AdminQuizController(AdminQuizService adminQuizService) {
        this.adminQuizService = adminQuizService;
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveQuizzes() {
        logger.info("Starting getAllActiveQuizzes");

        return ResponseEntity.ok(adminQuizService.getAllActiveQuizzes());
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> getAllInactiveQuizzes() {
        logger.info("Starting getAllInactiveQuizzes");

        return ResponseEntity.ok(adminQuizService.getAllInactiveQuizzes());
    }

    @GetMapping("/{nodeId}")
    public ResponseEntity<?> getQuiz(@PathVariable String nodeId) {
        logger.info("Starting getQuiz");

        return ResponseEntity.ok(adminQuizService.getQuiz(nodeId));
    }

    @PostMapping("/create")
    public ResponseEntity<QuizResponseDTO> createQuiz(@RequestBody QuizRequest request) {
        logger.info("Starting createQuiz");

        QuizResponseDTO createdQuiz = adminQuizService.createQuiz(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdQuiz);
    }

    @PostMapping("/update/{nodeId}")
    public ResponseEntity<?> updateQuiz(@PathVariable String nodeId,
                                        @RequestBody QuizRequest request) {
        logger.info("Starting updateQuiz");

        return ResponseEntity.ok(adminQuizService.updateQuiz(nodeId, request));
    }

    @PostMapping("/set-active/{nodeId}")
    public ResponseEntity<?> setQuizActive(@PathVariable String nodeId) {
        logger.info("Starting setQuizActive");

        adminQuizService.setQuizActive(nodeId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set-inactive/{nodeId}")
    public ResponseEntity<?> setQuizInactive(@PathVariable String nodeId) {
        logger.info("Starting setQuizInactive");

        adminQuizService.setQuizInactive(nodeId);

        return ResponseEntity.noContent().build();
    }
}
