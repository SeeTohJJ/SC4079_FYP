package com.SeeTohJJ.Backend.study.service.adaptive;

public interface EloService {

    void updateUserElo(Long userId, String subtopicId, String nodeId, boolean isCorrectAnswer);
}
