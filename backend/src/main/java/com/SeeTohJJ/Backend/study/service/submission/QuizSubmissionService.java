package com.SeeTohJJ.Backend.study.service.submission;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultDTO;

public interface QuizSubmissionService {

    boolean gradeAnswer(String nodeId, String optionSelected);
    void completeQuiz(Long userId, QuizResultDTO quizResult);
    String getQuizHint(Long userId, String nodeId);
    String getQuizExplanation(String nodeId);
}
