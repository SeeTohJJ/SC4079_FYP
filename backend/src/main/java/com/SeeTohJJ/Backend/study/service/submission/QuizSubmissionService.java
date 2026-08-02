package com.SeeTohJJ.Backend.study.service.submission;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;
import com.SeeTohJJ.Backend.study.dto.result.QuizSubmissionDTO;

public interface QuizSubmissionService {

    boolean gradeAnswer(String nodeId, String optionSelected);
    QuizResultResponseDTO completeQuiz(Long userId, QuizSubmissionDTO quizResult);
    String getQuizHint(Long userId, String nodeId);
    String getQuizExplanation(String nodeId);
}
