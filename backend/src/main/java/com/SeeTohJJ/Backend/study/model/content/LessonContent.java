package com.SeeTohJJ.Backend.study.model.content;

import com.SeeTohJJ.Backend.study.model.StudyNode;

import java.time.LocalDateTime;

public class LessonContent extends StudyNode {

    private String nodeId;
    private String title;
    private String content;
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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
