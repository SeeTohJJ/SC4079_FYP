package com.SeeTohJJ.Backend.study.model.chain;

public class ChainTemplate {

    public enum ChainType {
        TUTORIAL,
        STANDARD,
        PRACTICE
    }

    private ChainType chainType;
    private int orderInChain;
    private String nodeType;
    private int contentSequence;

    public int getOrderInChain() {
        return orderInChain;
    }

    public ChainType getChainType() {
        return chainType;
    }

    public void setChainType(ChainType chainType) {
        this.chainType = chainType;
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


}
