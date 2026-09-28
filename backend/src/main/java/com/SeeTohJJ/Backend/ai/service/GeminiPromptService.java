package com.SeeTohJJ.Backend.ai.service;

import com.SeeTohJJ.Backend.ai.dto.QuizGenerationContext;

import java.util.List;

public interface GeminiPromptService {

    String test();
    String buildQuizPrompt(QuizGenerationContext context, String learningObjectives);
    String buildReviewPrompt(QuizGenerationContext context, List<String> nodeContents);

}
