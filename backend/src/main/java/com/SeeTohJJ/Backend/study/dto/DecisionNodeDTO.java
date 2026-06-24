package com.SeeTohJJ.Backend.study.dto;

import com.SeeTohJJ.Backend.study.dto.option.DecisionOptionDTO;

import java.util.List;

public class DecisionNodeDTO extends StudyNodeDTO {

    private String content;
    private String choice_A;
    private String choice_B;
    private String result_A;
    private String result_B;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChoice_A() {
        return choice_A;
    }

    public void setChoice_A(String choice_A) {
        this.choice_A = choice_A;
    }

    public String getChoice_B() {
        return choice_B;
    }

    public void setChoice_B(String choice_B) {
        this.choice_B = choice_B;
    }

    public String getResult_A() {
        return result_A;
    }

    public void setResult_A(String result_A) {
        this.result_A = result_A;
    }

    public String getResult_B() {
        return result_B;
    }

    public void setResult_B(String result_B) {
        this.result_B = result_B;
    }
}
