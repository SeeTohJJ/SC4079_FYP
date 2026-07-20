package com.SeeTohJJ.Backend.study.service.submission;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultDTO;

public interface QuizSubmissionService {

    boolean gradeAnswer(String nodeId, String optionSelected);
    void saveQuestionAttemptHistory(Long userId, String nodeId, boolean isCorrectAnswer, int timeTaken);

    void completeQuiz(Long userId, QuizResultDTO quizResult);
}
