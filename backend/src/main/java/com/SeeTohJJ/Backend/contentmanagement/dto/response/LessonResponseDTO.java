package com.SeeTohJJ.Backend.contentmanagement.dto.response;

import java.time.LocalDateTime;

public class LessonResponseDTO {

    private String nodeId;
    private String topicId;
    private String subtopicId;
    private String title;
    private int orderIndex;
    private int requiredMastery;
    private boolean isActive;
    private String content;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getSubtopicId() {
        return subtopicId;
    }

    public void setSubtopicId(String subtopicId) {
        this.subtopicId = subtopicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getRequiredMastery() {
        return requiredMastery;
    }

    public void setRequiredMastery(int requiredMastery) {
        this.requiredMastery = requiredMastery;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
