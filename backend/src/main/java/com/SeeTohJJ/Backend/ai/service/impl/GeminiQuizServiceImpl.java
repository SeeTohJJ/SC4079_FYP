package com.SeeTohJJ.Backend.ai.service.impl;

import com.SeeTohJJ.Backend.ai.dto.GeminiGeneratedQuizDTO;
import com.SeeTohJJ.Backend.ai.dto.QuizGenerationContext;
import com.SeeTohJJ.Backend.ai.service.GeminiPromptService;
import com.SeeTohJJ.Backend.ai.service.GeminiQuizService;
import com.SeeTohJJ.Backend.study.service.progress.UserStudyPathService;
import com.SeeTohJJ.Backend.topic.service.SubTopicService;
import com.SeeTohJJ.Backend.topic.service.TopicService;
import com.SeeTohJJ.Backend.user.model.UserProfile;
import com.SeeTohJJ.Backend.user.model.UserTopicMastery;
import com.SeeTohJJ.Backend.user.service.UserService;
import com.SeeTohJJ.Backend.user.service.mastery.UserTopicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.gaos.models.interactions.*;
import com.google.genai.gaos.models.operations.CreateInteractionRequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GeminiQuizServiceImpl implements GeminiQuizService {

    private static final Logger logger = LoggerFactory.getLogger(GeminiQuizServiceImpl.class);

    private final Client client;
    private final GeminiPromptService promptService;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final UserTopicService userTopicService;
    private final UserStudyPathService userStudyPathService;
    private final TopicService topicService;
    private final SubTopicService subTopicService;

    public GeminiQuizServiceImpl(
            Client client,
            GeminiPromptService promptService,
            ObjectMapper objectMapper,
            UserService userService,
            UserTopicService userTopicService,
            UserStudyPathService userStudyPathService,
            TopicService topicService,
            SubTopicService subTopicService) {
        this.client = client;
        this.promptService = promptService;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.userTopicService = userTopicService;
        this.userStudyPathService = userStudyPathService;
        this.topicService = topicService;
        this.subTopicService = subTopicService;
    }

    private GeminiGeneratedQuizDTO generateQuiz(
            QuizGenerationContext context,
            String learningObjectives) {

        String prompt = promptService.buildQuizPrompt(
                context,
                learningObjectives
        );

        logger.info("Gemini prompt: {}", prompt);

        CreateModelInteraction params =
                CreateModelInteraction.builder()
                        .model(Model.of("gemini-3.8-flash"))
                        .input(InteractionsInput.of(prompt))
                        .build();

        Interaction interaction =
                client.interactions
                        .create(CreateInteractionRequestBody.of(params))
                        .interaction()
                        .get();

        logger.info("Gemini interaction: {}", interaction);

        String json = null;

        for (var step : interaction.steps().orElseThrow()) {
            if (step instanceof ModelOutputStep modelOutputStep) {
                for (var content : modelOutputStep.content().orElseThrow()) {
                    if (content instanceof TextContent textContent) {
                        json = textContent.text().orElse(null);
                    }
                }
            }
        }

        if (json == null || json.isBlank()) {
            throw new RuntimeException(
                    "Gemini returned no text content"
            );
        }

        logger.info("Gemini JSON: {}", json);

        json = json.trim();

        // Remove markdown code fences if Gemini returns them
        if (json.startsWith("```json")) {
            json = json.substring(7);
        } else if (json.startsWith("```")) {
            json = json.substring(3);
        }

        if (json.endsWith("```")) {
            json = json.substring(0, json.length() - 3);
        }

        json = json.trim();

        GeminiGeneratedQuizDTO quiz;

        try {
            quiz = objectMapper.readValue(
                    json,
                    GeminiGeneratedQuizDTO.class
            );
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse Gemini JSON: {}", json, e);

            throw new RuntimeException(
                    "Gemini returned invalid JSON",
                    e
            );
        }

        validateQuiz(quiz);

        quiz.setTitle(topicService.getTopicName(context.getTopic()));
        quiz.setNodeId(context.getSubtopic() + "_quiz");

        return quiz;
    }

    private void validateQuiz(GeminiGeneratedQuizDTO quiz) {

        if (quiz.getQuestion() == null ||
                quiz.getQuestion().isBlank()) {

            throw new RuntimeException(
                    "Invalid generated question"
            );
        }

        if (quiz.getOptionA() == null ||
            quiz.getOptionB() == null ||
            quiz.getOptionC() == null ||
            quiz.getOptionD() == null) {

            throw new RuntimeException(
                    "Quiz must contain exactly four options"
            );
        }

        if (quiz.getCorrectAnswer() == null) {

            throw new RuntimeException(
                    "Missing correct answer"
            );
        }

        boolean answerExists =
                quiz.getCorrectAnswer().equals(quiz.getOptionA()) ||
                quiz.getCorrectAnswer().equals(quiz.getOptionB()) ||
                quiz.getCorrectAnswer().equals(quiz.getOptionC()) ||
                quiz.getCorrectAnswer().equals(quiz.getOptionD()) ||
                quiz.getOptionA().isBlank() ||
                quiz.getOptionB().isBlank() ||
                quiz.getOptionC().isBlank() ||
                quiz.getOptionD().isBlank();

        if (!answerExists) {
            throw new RuntimeException(
                    "Correct answer does not match an option"
            );
        }
    }

    @Override
    public GeminiGeneratedQuizDTO getQuizContent(Long userId) {
        logger.info("Start getQuizContent");

        UserProfile user = userService.getUserProfile(userId);
        String currentSubtopicId = userStudyPathService.getCurrentSubtopic(userId);
        String currentTopicId = currentSubtopicId.substring(0, 4);

        QuizGenerationContext context = new QuizGenerationContext();
        context.setAge(user.getAge());
        context.setIncome(user.getIncome());
        context.setCountry(user.getCountry());
        context.setEmployment_status(user.getEmploymentStatus());
        context.setSubtopic(currentSubtopicId);
        context.setTopic(currentTopicId);
        context.setEloRating(userTopicService.getAverageElo(userId, currentTopicId));
        context.setMasteryScore(userTopicService.getAveragePKnow(userId, currentTopicId));

        return generateQuiz(context, subTopicService.getName(currentSubtopicId));
    }
}
