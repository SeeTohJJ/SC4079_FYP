package com.SeeTohJJ.Backend.contentmanagement.dto;

public class CreateTopicRequest {

    private String topicName;
    private String topicDescription;

    public CreateTopicRequest() {
    }

    public CreateTopicRequest(String topicName, String topicDescription) {
        this.topicName = topicName;
        this.topicDescription = topicDescription;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }
}
