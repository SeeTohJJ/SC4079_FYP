package com.SeeTohJJ.Backend.study.service.content;

import com.SeeTohJJ.Backend.ai.dto.GeminiGeneratedQuizDTO;
import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;

import java.util.List;

public interface ContentRetrievalService {

    LessonContentDTO getLessonNodeContent(String nodeId);
    QuizContentDTO getQuizContent(String nodeId);
    DecisionContentDTO getDecisionNodeContent(String nodeId);
    EventContentDTO getEventNodeContent(String nodeId);
    double getQuestionRating(String nodeId);
    String getHint(String nodeId);
    String getExplanation(String nodeId);
    List<String> getMistakeHistoryContent(List<String> nodeIds);
}
