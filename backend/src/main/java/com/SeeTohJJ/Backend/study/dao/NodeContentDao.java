package com.SeeTohJJ.Backend.study.dao;

import com.SeeTohJJ.Backend.contentmanagement.dto.request.LessonRequest;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.LessonResponseDTO;
import com.SeeTohJJ.Backend.contentmanagement.dto.response.QuizResponseDTO;
import com.SeeTohJJ.Backend.study.dto.node.DecisionContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.EventContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.LessonContentDTO;
import com.SeeTohJJ.Backend.study.dto.node.QuizContentDTO;

import java.util.List;

public interface NodeContentDao {

    LessonContentDTO getLessonNodeContent(String nodeId);
    QuizContentDTO getQuizNodeContent(String nodeId);
    DecisionContentDTO getDecisionNodeContent(String nodeId);
    EventContentDTO getEventNodeContent(String nodeId);
    double getQuestionRating(String nodeId);
    String getQuizHint(String nodeId);
    String getQuizExplanation(String nodeId);

    List<LessonResponseDTO> getAllLessons();
    LessonResponseDTO getLesson(String nodeId);
    void createLesson(String nodeId,
                      String topicId,
                      String subtopicId,
                      String title,
                      int orderIndex,
                      int requiredMastery,
                      String content);
    void updateLesson(String nodeId,
                      String topicId,
                      String subtopicId,
                      String title,
                      int orderIndex,
                      int requiredMastery,
                      String content);
    void setLessonInactive(String nodeId);
    void setLessonActive(String nodeId);

    List<QuizResponseDTO> getAllQuizzes();
    QuizResponseDTO getQuiz(String nodeId);
    void createQuiz(String nodeId,
                    String topicId,
                    String subtopicId,
                    String title,
                    int orderIndex,
                    int requiredMastery,
                    String content,
                    String optionA,
                    String optionB,
                    String optionC,
                    String optionD,
                    String correctAnswer,
                    int difficultyRating,
                    String hint,
                    String explanation);
    void updateQuiz(String nodeId,
                    String topicId,
                    String subtopicId,
                    String title,
                    int orderIndex,
                    int requiredMastery,
                    String content,
                    String optionA,
                    String optionB,
                    String optionC,
                    String optionD,
                    String correctAnswer,
                    int difficultyRating,
                    String hint,
                    String explanation);
    void setQuizInactive(String nodeId);
    void setQuizActive(String nodeId);

}
