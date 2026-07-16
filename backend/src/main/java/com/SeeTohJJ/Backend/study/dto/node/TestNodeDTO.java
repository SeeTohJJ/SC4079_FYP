package com.SeeTohJJ.Backend.study.dto.node;

import java.util.List;

public class TestNodeDTO extends StudyNodeDTO {

    private List<QuizNodeDTO> questions;

    public List<QuizNodeDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizNodeDTO> questions) {
        this.questions = questions;
    }
}
