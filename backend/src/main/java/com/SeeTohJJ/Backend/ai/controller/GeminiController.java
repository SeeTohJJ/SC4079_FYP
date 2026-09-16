package com.SeeTohJJ.Backend.ai.controller.impl;

import com.SeeTohJJ.Backend.ai.controller.GeminiController;
import com.SeeTohJJ.Backend.ai.service.GeminiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiControllerImpl implements GeminiController {

    private final GeminiService geminiService;

    public GeminiControllerImpl(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/api/ai/test")
    public String testGemini() {
        return geminiService.test();
    }
}