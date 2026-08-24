package com.SeeTohJJ.Backend.study.service.result;

import com.SeeTohJJ.Backend.study.dto.result.QuizResultResponseDTO;

public interface QuizResultService {

    QuizResultResponseDTO buildQuizResult(Long userId,
                                          String subtopicId,
                                          boolean correct,
                                          int previousMastery,
                                          int updatedMastery,
                                          boolean newChainCreated,
                                          int timeTaken,
                                          int waterReward);
}
