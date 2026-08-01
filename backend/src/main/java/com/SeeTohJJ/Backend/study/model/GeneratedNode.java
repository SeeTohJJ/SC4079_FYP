package com.SeeTohJJ.Backend.study.model;

public class GeneratedNode {
    private final String nodeId;
    private final String nodeType;
    private final int position;
    private final boolean unlocked;

    public GeneratedNode(
            String nodeId,
            String nodeType,
            int position,
            boolean unlocked) {

        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.position = position;
        this.unlocked = unlocked;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public int getPosition() {
        return position;
    }

    public boolean isUnlocked() {
        return unlocked;
    }
}
