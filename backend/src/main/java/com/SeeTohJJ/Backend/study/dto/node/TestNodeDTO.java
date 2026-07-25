package com.SeeTohJJ.Backend.study.dto.node;

import java.util.List;

public class TestNodeDTO extends StudyNodeDTO {

    private List<QuizContentDTO> questions;

    public List<QuizContentDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizContentDTO> questions) {
        this.questions = questions;
    }
}
