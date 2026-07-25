package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.study.dto.node.DecisionNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonNodeDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizNodeDTO;

public interface NodeContentDao {

    LessonNodeDTO getLessonNodeContent(String nodeId);
    QuizNodeDTO getQuizNodeContent(String nodeId);
    DecisionNodeDTO getDecisionNodeContent(String nodeId);
    EventNodeDTO getEventNodeContent(String nodeId);
    double getQuestionRating(String nodeId);

}
