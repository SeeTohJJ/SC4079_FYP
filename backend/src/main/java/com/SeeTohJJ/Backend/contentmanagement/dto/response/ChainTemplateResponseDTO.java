package com.SeeTohJJ.Backend.contentmanagement.dto.response;

import java.time.LocalDateTime;

public class ChainTemplateResponseDTO {

    private int chainTemplateId;
    private String chainType;
    private int orderInChain;
    private String nodeType;
    private int contentSequence;
    private boolean isActive;

    public int getChainTemplateId() {
        return chainTemplateId;
    }

    public void setChainTemplateId(int chainTemplateId) {
        this.chainTemplateId = chainTemplateId;
    }

    public String getChainType() {
        return chainType;
    }

    public void setChainType(String chainType) {
        this.chainType = chainType;
    }

    public int getOrderInChain() {
        return orderInChain;
    }

    public void setOrderInChain(int orderInChain) {
        this.orderInChain = orderInChain;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public int getContentSequence() {
        return contentSequence;
    }

    public void setContentSequence(int contentSequence) {
        this.contentSequence = contentSequence;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
