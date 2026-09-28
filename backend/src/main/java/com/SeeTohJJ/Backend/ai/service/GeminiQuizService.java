package com.SeeTohJJ.Backend.ai.service;

import com.SeeTohJJ.Backend.ai.dto.GeminiGeneratedQuizDTO;

public interface GeminiQuizService {

    GeminiGeneratedQuizDTO getQuizContent(Long userId);
    GeminiGeneratedQuizDTO getReviewContent(Long userId);

}
