package com.SeeTohJJ.Backend.contentmanagement.dto.request;

public class UpdateTopicRequest {

    private String topicName;
    private String topicDescription;

    public UpdateTopicRequest() {
    }

    public UpdateTopicRequest(String topicName, String topicDescription) {
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
