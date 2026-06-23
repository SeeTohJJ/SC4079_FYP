package com.SeeTohJJ.Backend.study.dto;

import com.SeeTohJJ.Backend.study.model.StudyNode;

public class StudyNodePathDTO {

    private String nodeId;
    private StudyNode.NodeType nodeType;
    private int positionIndex;
    private boolean isUnlocked;
    private boolean isCompleted;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public StudyNode.NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(StudyNode.NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
