package com.bsoft.accordion.core.graph;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sky on 2018/1/29.
 */
public class Node {

    String nodeId;//

    List<Node> next = new ArrayList<>();

    List<Node> pre = new ArrayList<>();

    public Node(){
    }

    public Node(String taskId) {
        this.nodeId = taskId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<Node> getNext() {
        return next;
    }

    public void setNext(List<Node> next) {
        this.next = next;
    }

    public List<Node> getPre() {
        return pre;
    }

    public void setPre(List<Node> pre) {
        this.pre = pre;
    }
}
