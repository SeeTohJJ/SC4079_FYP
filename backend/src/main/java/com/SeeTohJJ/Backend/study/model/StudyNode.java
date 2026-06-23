package com.SeeTohJJ.Backend.study.model;

import java.time.LocalDateTime;

public class Node {

    private enum NodeType {
        LESSON,
        QUIZ,
        DECISION,
        TEST
    }

    private String NodeId;
    private String TopicId;
    private NodeType Type;
    private String Title;
    private String Content;
    private int orderIndex;
    private int requiredMastery;
    private LocalDateTime lastUpdated;

    public String getNodeId() {
        return NodeId;
    }

    public void setNodeId(String nodeId) {
        NodeId = nodeId;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

    public NodeType getType() {
        return Type;
    }

    public void setType(NodeType type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
