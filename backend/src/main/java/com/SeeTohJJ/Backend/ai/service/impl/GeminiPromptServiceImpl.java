package com.SeeTohJJ.Backend.ai.service.impl;

import com.SeeTohJJ.Backend.ai.service.GeminiService;
import com.google.genai.Client;
import com.google.genai.gaos.models.interactions.CreateModelInteraction;
import com.google.genai.gaos.models.interactions.Interaction;
import com.google.genai.gaos.models.interactions.InteractionsInput;
import com.google.genai.gaos.models.interactions.Model;
import com.google.genai.gaos.models.operations.CreateInteractionRequestBody;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final Client client;

    public GeminiServiceImpl(Client client) {
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

        return interaction.outputText().orElse("");
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
}