package com.SeeTohJJ.Backend.contentmanagement.controller;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.LessonRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.LessonResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminLessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lessons")
public class AdminLessonController {

    private static final Logger logger = LoggerFactory.getLogger(AdminLessonController.class);

    private final AdminLessonService adminLessonService;

    public AdminLessonController(AdminLessonService adminLessonService) {
        this.adminLessonService = adminLessonService;
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveLessons() {
        logger.info("Starting getAllActiveLessons");

        return ResponseEntity.ok(adminLessonService.getAllActiveLessons());
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> getAllInactiveLessons() {
        logger.info("Starting getAllInactiveLessons");

        return ResponseEntity.ok(adminLessonService.getAllInactiveLessons());
    }

    @GetMapping("/{nodeId}")
    public ResponseEntity<?> getLesson(@PathVariable String nodeId) {
        logger.info("Starting getLesson");

        return ResponseEntity.ok(adminLessonService.getLesson(nodeId));
    }

    @PostMapping("/create")
    public ResponseEntity<LessonResponseDTO> createLesson(@RequestBody LessonRequest request) {
        logger.info("Starting createLesson");

        LessonResponseDTO createdLesson = adminLessonService.createLesson(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdLesson);
    }

    @PostMapping("/update/{nodeId}")
    public ResponseEntity<?> updateLesson(@PathVariable String nodeId,
                                            @RequestBody LessonRequest request) {
        logger.info("Starting updateLesson");

        return ResponseEntity.ok(adminLessonService.updateLesson(nodeId, request));
    }

    @PostMapping("/set-active/{nodeId}")
    public ResponseEntity<?> setLessonActive(@PathVariable String nodeId) {
        logger.info("Starting setLessonActive");

        adminLessonService.setLessonActive(nodeId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set-inactive/{nodeId}")
    public ResponseEntity<?> setLessonInactive(@PathVariable String nodeId) {
        logger.info("Starting setLessonInactive");

        adminLessonService.setLessonInactive(nodeId);

        return ResponseEntity.noContent().build();
    }
}
