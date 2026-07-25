package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;

public interface NodeContentDao {

    LessonContentDTO getLessonNodeContent(String nodeId);
    QuizContentDTO getQuizNodeContent(String nodeId);
    DecisionContentDTO getDecisionNodeContent(String nodeId);
    EventContentDTO getEventNodeContent(String nodeId);
    double getQuestionRating(String nodeId);
    String getQuizHint(String nodeId);
    String getQuizExplanation(String nodeId);
}
