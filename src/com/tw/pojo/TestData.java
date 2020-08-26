package com.tw.pojo;

public class TestData {

    private String nodeId;
    private String parentId;
    private String context;
    private boolean leaf;
    private String level;
    private boolean spread;

    public TestData(String nodeId, String parentId, String context, boolean leaf, String level, boolean spread) {
        this.nodeId = nodeId;
        this.parentId = parentId;
        this.context = context;
        this.leaf = leaf;
        this.level = level;
        this.spread = spread;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }
}
