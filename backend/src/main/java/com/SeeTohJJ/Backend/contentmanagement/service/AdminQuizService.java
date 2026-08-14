package com.SeeTohJJ.Backend.contentmanagement.service;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.QuizRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.QuizResponseDTO;

import java.util.List;

public interface AdminQuizService {

    List<QuizResponseDTO> getAllQuizzes();
    QuizResponseDTO getQuiz(String nodeId);
    QuizResponseDTO createQuiz(QuizRequest request);
    QuizResponseDTO updateQuiz(String nodeId, QuizRequest request);
    void setQuizInactive(String nodeId);
    void setQuizActive(String nodeId);
}
