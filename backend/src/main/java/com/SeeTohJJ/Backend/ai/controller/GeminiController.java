package com.SeeTohJJ.Backend.ai.controller;

import com.SeeTohJJ.Backend.ai.service.GeminiPromptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {
    private static final Logger logger = LoggerFactory.getLogger(GeminiController.class);

    private final GeminiPromptService geminiService;

    public GeminiController(GeminiPromptService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/test")
    public String testGemini() {
        logger.info("testGemini");

        return geminiService.test();
    }
}