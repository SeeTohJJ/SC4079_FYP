package com.SeeTohJJ.Backend.study.model.content;

import com.SeeTohJJ.Backend.study.model.StudyNode;

import java.time.LocalDateTime;

public class DecisionContent extends StudyNode {

    private String nodeId;
    private String title;
    private String content;
    private String choice_A;
    private String choice_B;
    private String result_A;
    private String result_B;
    private LocalDateTime lastUpdated;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
