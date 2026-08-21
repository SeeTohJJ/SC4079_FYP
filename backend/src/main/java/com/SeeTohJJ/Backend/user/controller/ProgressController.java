package com.SeeTohJJ.Backend.user.controller;

import com.SeeTohJJ.Backend.auth.service.JwtService;
import com.SeeTohJJ.Backend.study.controller.EnergyController;
import com.SeeTohJJ.Backend.study.service.gameplay.EnergyService;
import com.SeeTohJJ.Backend.user.dto.CompletedLessonResponseDTO;
import com.SeeTohJJ.Backend.user.dto.ProgressResponseDTO;
import com.SeeTohJJ.Backend.user.service.ProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);

    private final ProgressService progressService;
    private final JwtService jwtService;

    @Autowired
    public ProgressController(ProgressService progressService,
                              JwtService jwtService) {
        this.progressService = progressService;
        this.jwtService = jwtService;
    }

    @GetMapping("/get_progress")
    public ProgressResponseDTO getProgress(@RequestHeader("Authorization") String authHeader) {
        logger.info("Starting getProgress");

        Long userId = jwtService.extractUserId(authHeader.substring(7));

        return progressService.getProgress(userId);
    }

    @GetMapping("/get_completed_lessons")
    public List<CompletedLessonResponseDTO> getCompletedLessons(@RequestHeader("Authorization") String authHeader) {
        logger.info("Starting getCompletedLessons");

        Long userId = jwtService.extractUserId(authHeader.substring(7));

        return progressService.getCompletedLessons(userId);
    }
}
