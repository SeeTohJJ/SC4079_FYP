package com.SeeTohJJ.Backend.study.dto;

import java.time.LocalDateTime;

public abstract class NodeResponseDTO {

    public enum NodeType {
        LESSON,
        QUIZ,
        DECISION,
        TEST
    }

    public enum NodeStatus {
        LOCKED,
        UNLOCKED,
        COMPLETED
    }
    private String nodeId;
    private String topicId;

    private String isTutorial;

    private String title;

    private NodeType type;

    private NodeStatus status;

    private int masteryReward;

    private String isCompleted;

    private LocalDateTime unlockedAt;

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

    public String getIsTutorial() {
        return isTutorial;
    }

    public void setIsTutorial(String isTutorial) {
        this.isTutorial = isTutorial;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public int getMasteryImpact() {
        return masteryReward;
    }

    public void setMasteryImpact(int masteryReward) {
        this.masteryReward = masteryReward;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public LocalDateTime getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(LocalDateTime unlockedAt) {
        this.unlockedAt = unlockedAt;
    }
}
