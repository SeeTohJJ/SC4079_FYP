package com.SeeTohJJ.Backend.ai.service.impl;

import com.SeeTohJJ.Backend.ai.controller.GeminiController;
import com.SeeTohJJ.Backend.ai.dto.QuizGenerationContext;
import com.SeeTohJJ.Backend.ai.service.GeminiPromptService;
import com.google.genai.Client;
import com.google.genai.gaos.models.interactions.*;
import com.google.genai.gaos.models.operations.CreateInteractionRequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiPromptServiceImpl implements GeminiPromptService {
    private static final Logger logger = LoggerFactory.getLogger(GeminiPromptServiceImpl.class);

    private final Client client;

    public GeminiPromptServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public String test() {
        System.out.println("Calling Gemini...");

        CreateModelInteraction params =
                CreateModelInteraction.builder()
                        .model(Model.of("gemini-3.8-flash"))
                        .input(InteractionsInput.of(
                                "Generate a simple financial literacy question about budgeting."
                        ))
                        .build();

        System.out.println("Sending request to Gemini...");

        Interaction interaction = client.interactions
                .create(CreateInteractionRequestBody.of(params))
                .interaction()
                .get();

        System.out.println("Gemini returned!");

        String json = interaction.steps()
                .orElseThrow(() -> new RuntimeException(
                        "Gemini returned no steps"
                ))
                .stream()
                .filter(step -> step instanceof ModelOutputStep)
                .map(step -> (ModelOutputStep) step)
                .flatMap(step -> step.content().orElse(List.of()).stream())
                .filter(content -> content instanceof TextContent)
                .map(content -> ((TextContent) content).text().orElse(""))
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Gemini returned no text content"
                ));

        return json;
    }

    private String determineDifficulty(double elo) {

        if (elo < 1000) {
            return "BEGINNER";
        }

        if (elo < 1400) {
            return "INTERMEDIATE";
        }

        return "ADVANCED";
    }

    public String buildQuizPrompt(QuizGenerationContext context, String learningObjectives) {

        String difficulty = determineDifficulty(context.getEloRating());

        return """
                You are a financial literacy question generator
                for a mobile learning application.

                Generate ONE multiple-choice question.

                USER PROFILE:
                Occupation: %s
                Age: %d
                Monthly income: %d
                Country: %s

                LEARNING STATE:
                Topic: %s
                Subtopic: %s
                Mastery: %.2f
                Difficulty: %s

                LEARNING OBJECTIVES:
                %s

                REQUIREMENTS:
                1. Generate exactly one multiple-choice question.
                2. Generate exactly four options.
                3. There must be exactly one correct answer.
                4. Match the requested difficulty.
                5. Personalise the scenario using the user's occupation
                   where appropriate.
                6. Use income only when useful for the scenario.
                7. Do not assume the user's actual financial behaviour.
                8. Do not provide personalised financial advice.
                9. Do not recommend specific financial products.
                10. Test only the supplied learning objectives.
                11. Provide an hint to guide the user the correct answer.
                12. Return ONLY the requested JSON structure.
                
                The "correct answer" field must match one of the four options.
                The "hint" field must provide a hint for the question.
                Do not use an "options" array.
                Do not use "correct_answer_index".
                Do not wrap the JSON in markdown code fences.
                Return JSON only.
                
                RETURN EXACTLY THIS JSON STRUCTURE:
                
                {
                  "question": "string",
                  "optionA": "string",
                  "optionB": "string",
                  "optionC": "string",
                  "optionD": "string",
                  "correctAnswer": "string",
                  "hint": "string"
                }
                
                """.formatted(
                context.getEmployment_status(),
                context.getAge(),
                context.getIncome(),
                context.getCountry(),
                context.getTopic(),
                context.getSubtopic(),
                context.getMasteryScore(),
                difficulty,
                learningObjectives
        );
    }
}