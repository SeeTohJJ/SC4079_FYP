package com.SeeTohJJ.Backend.study.model;

import java.time.LocalDateTime;

public class StudyNode {

    public enum NodeType {
        LESSON,
        QUIZ,
        DECISION,
        TEST
    }

    private String nodeId;
    private String topicId;
    private NodeType nodeType;
    private String title;
    private int orderIndex;
    private int requiredMastery;
    private LocalDateTime lastUpdated;

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

    public NodeType getType() {
        return nodeType;
    }

    public void setType(NodeType nodeType) {
        this.nodeType = nodeType;
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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
