package com.SeeTohJJ.Backend.study.service.result;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;

public interface QuizResultService {

    QuizResultResponseDTO buildQuizResult(Long userId, String subtopicId, boolean correct, double previousPKnow, double updatedPKnow, boolean newChainCreated);
}
