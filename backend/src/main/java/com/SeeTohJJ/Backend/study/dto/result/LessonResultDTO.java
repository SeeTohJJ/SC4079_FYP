package com.SeeTohJJ.Backend.study.dto.result;

public class LessonResultDTO {

    private Long userId;
    private String nodeId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
