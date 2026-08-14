package com.SeeTohJJ.Backend.contentmanagement.controller;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.ChainTemplateRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.request.QuizRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.ChainTemplateResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.service.AdminStudyChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/study-chain")
public class AdminStudyChainTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(AdminStudyChainTemplateController.class);

    private final AdminStudyChainService adminStudyChainService;

    public AdminStudyChainTemplateController(AdminStudyChainService adminStudyChainService) {
        this.adminStudyChainService = adminStudyChainService;
    }

    @GetMapping
    public ResponseEntity<?> getAllChainTemplates() {
        logger.info("Starting getAllChainTemplates");

        return ResponseEntity.ok(adminStudyChainService.getAllChainTemplates());
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<?> getChainTemplate(@PathVariable int templateId) {
        logger.info("Starting getChainTemplate");

        return ResponseEntity.ok(adminStudyChainService.getChainTemplate(templateId));
    }

    @PostMapping("/create-chain-template")
    public ResponseEntity<ChainTemplateResponseDTO> createChainTemplate(@RequestBody ChainTemplateRequest request) {
        logger.info("Starting createChainTemplate");

        ChainTemplateResponseDTO createdTemplate = adminStudyChainService.createChainTemplate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTemplate);
    }

    @PostMapping("/update/{templateId}")
    public ResponseEntity<?> updateChainTemplate(@PathVariable int templateId,
                                                 @RequestBody ChainTemplateRequest request) {
        logger.info("Starting updateChainTemplate");

        return ResponseEntity.ok(adminStudyChainService.updateChainTemplate(templateId, request));
    }

    @PostMapping("/set-active/{templateId}")
    public ResponseEntity<?> setChainTemplateActive(@PathVariable int templateId) {
        logger.info("Starting setChainTemplateActive");

        adminStudyChainService.setChainTemplateActive(templateId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/set-inactive/{templateId}")
    public ResponseEntity<?> setChainTemplateInactive(@PathVariable int templateId) {
        logger.info("Starting setChainTemplateInactive");

        adminStudyChainService.setChainTemplateInactive(templateId);

        return ResponseEntity.noContent().build();
    }
}
