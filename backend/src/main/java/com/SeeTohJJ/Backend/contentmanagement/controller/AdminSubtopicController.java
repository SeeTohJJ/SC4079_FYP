package com.SeeTohJJ.Backend.contentmanagement.controller;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.CreateSubtopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.UpdateSubtopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminSubtopicService;
import com.SeeTohJJ.Backend.topic.dto.SubtopicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/subtopics")
public class AdminSubtopicController {
    private static final Logger logger = LoggerFactory.getLogger(AdminSubtopicController.class);

    private final AdminSubtopicService adminSubtopicService;

    public AdminSubtopicController(AdminSubtopicService adminSubtopicService) {
        this.adminSubtopicService = adminSubtopicService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSubtopics() {
        logger.info("Starting getAllSubtopics");

        return ResponseEntity.ok(adminSubtopicService.getAllSubtopics());
    }

    @GetMapping("/{subtopicId}")
    public ResponseEntity<?> getSubtopic(@PathVariable String subtopicId) {
        logger.info("Starting getSubtopic");

        return ResponseEntity.ok(adminSubtopicService.getSubtopic(subtopicId));
    }

    @PostMapping("/create")
    public ResponseEntity<SubtopicDTO> createSubtopic(@RequestBody CreateSubtopicRequest request) {
        logger.info("Starting createSubtopic");

        SubtopicDTO createdSubtopic = adminSubtopicService.createSubtopic(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSubtopic);
    }

    @PostMapping("/update/{subtopicId}")
    public ResponseEntity<?> updateSubtopic(@PathVariable String subtopicId,
                                         @RequestBody UpdateSubtopicRequest request) {
        logger.info("Starting updateSubtopic");

        return ResponseEntity.ok(adminSubtopicService.updateSubtopic(subtopicId, request));
    }

    @PostMapping("/set-inactive/{subtopicId}")
    public ResponseEntity<?> setSubtopicInactive(@PathVariable String subtopicId) {
        logger.info("Starting setSubtopicInactive");

        adminSubtopicService.setSubtopicInactive(subtopicId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set-active/{subtopicId}")
    public ResponseEntity<?> setSubtopicActive(@PathVariable String subtopicId) {
        logger.info("Starting setSubtopicActive");

        adminSubtopicService.setSubtopicActive(subtopicId);

        return ResponseEntity.noContent().build();
    }
}