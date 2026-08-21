package com.SeeTohJJ.Backend.user.dto;

import java.time.LocalDateTime;

public class CompletedLessonResponseDTO {

    private String nodeId;
    private String title;
    private String topicId;

    public CompletedLessonResponseDTO(String nodeId, String title, String topicId) {
        this.nodeId = nodeId;
        this.title = title;
        this.topicId = topicId;
    }

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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicIde(String topicId) {
        this.topicId = topicId;
    }
}
