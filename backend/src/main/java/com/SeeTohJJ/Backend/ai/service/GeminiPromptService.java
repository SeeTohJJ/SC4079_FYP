package com.SeeTohJJ.Backend.ai.service;

import com.SeeTohJJ.Backend.ai.dto.QuizGenerationContext;

public interface GeminiPromptService {

    String test();
    String buildQuizPrompt(QuizGenerationContext context, String learningObjectives);
}
