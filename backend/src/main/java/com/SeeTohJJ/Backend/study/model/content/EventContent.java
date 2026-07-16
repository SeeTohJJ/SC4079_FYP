package com.SeeTohJJ.Backend.study.model.content;

import com.SeeTohJJ.Backend.study.model.StudyNode;

import java.time.LocalDateTime;

public class EventContent extends StudyNode {

    private String nodeId;
    private String title;
    private String content;
    private String result;
    private LocalDateTime lastUpdated;

    @Override
    public String getNodeId() {
        return nodeId;
    }
}
