package com.SeeTohJJ.Backend.contentmanagement.controller;

import com.SeeTohJJ.Backend.contentmanagement.dto.CreateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.UpdateTopicRequest;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminTopicService;
import com.SeeTohJJ.Backend.topic.dto.TopicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/topics")
public class AdminTopicController {
    private static final Logger logger = LoggerFactory.getLogger(AdminTopicController.class);

    private final AdminTopicService adminTopicService;

    public AdminTopicController(AdminTopicService adminTopicService) {
        this.adminTopicService = adminTopicService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        logger.info("Starting getAllTopics");

        return ResponseEntity.ok(adminTopicService.getAllTopics());
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<?> getTopic(@PathVariable String topicId) {
        logger.info("Starting getTopic");

        return ResponseEntity.ok(adminTopicService.getTopic(topicId));
    }

    @PostMapping("/create-topic")
    public ResponseEntity<TopicDTO> createTopic(@RequestBody CreateTopicRequest request) {
        logger.info("Starting createTopic");

        TopicDTO createdTopic = adminTopicService.createTopic(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTopic);
    }

    @PutMapping("/update/{topicId}")
    public ResponseEntity<?> updateTopic(@PathVariable String topicId,
                                         @RequestBody UpdateTopicRequest request) {
        logger.info("Starting updateTopic");

        return ResponseEntity.ok(adminTopicService.updateTopic(topicId, request));
    }

    @DeleteMapping("/set-inactive/{topicId}")
    public ResponseEntity<?> setTopicInactive(@PathVariable String topicId) {
        logger.info("Starting setTopicInactive");

        adminTopicService.setTopicInactive(topicId);

        return ResponseEntity.noContent().build();
    }
}