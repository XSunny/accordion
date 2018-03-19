package com.bsoft.accordion.core.graph;


import com.bsoft.accordion.core.node.AbstractNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sky on 2018/1/29.
 *
 *  随机访问的双向链表，可以访问随机节点的前序节点和后序节点
 *
 *  多线程情况下，只进行get操作，故不做同步
 *
 */
public class NodeGraph {

    private Map<String, Node> nodeMap = new HashMap<>();//只读 不需要同步

    private Map<String, List<String>> travelMap = new ConcurrentHashMap<>();// 可能会有重复刷新，引入了同步

    private String firstNode = "Start#0";

    private String endNode = "End#0";

    public List<String> getNextNode(String id){
        List<String> nodes = new ArrayList<>();
        for (Node node:nodeMap.get(id).getNext()) {
            nodes.add(node.getNodeId());
        }
        return nodes;
    }

    public List<String> getPreNode(String id){
        List<String> nodes = new ArrayList<>();
        for (Node node:nodeMap.get(id).getPre()) {
            nodes.add(node.getNodeId());
        }
        return nodes;
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    public void addLink(AbstractNode source, AbstractNode direction){
        addLink(source.getNodeId(),direction.getNodeId());
    }

    public void addLink(String source, String direction){
        Node ns = this.nodeMap.get(source);
        Node nd = this.nodeMap.get(direction);
        if (ns == null){
            ns = new Node(source);
            this.nodeMap.put(source, ns);
        }
        if (nd == null){
            nd = new Node(direction);
            this.nodeMap.put(direction, nd);
        }
        ns.getNext().add(nd);
        nd.getPre().add(ns);
    }

    public void setNodeMap(Map<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public void addFirst2Map(String first){
        List<String> targetIds = new ArrayList<>();
        for (String nodeId :nodeMap.keySet()) {
            if (nodeMap.get(nodeId).getPre().size() ==0){//该节点没有前驱结点
                //addLink(first, nodeId);
                targetIds.add(nodeId);
            }
        }
        targetIds.forEach(t->addLink(first,t));
    }
    public void setFistNode(AbstractNode first){
        addFirst2Map(first.getNodeId());
        this.firstNode = first.getNodeId();
    }

    public String getFirstNode() {
        return firstNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNoe(AbstractNode end) {
        addEnd2Map(end.getNodeId());
        this.endNode = end.getNodeId();
    }

    private void addEnd2Map(String end) {
        List<String> targetIds = new ArrayList<>();
        for (String nodeId :nodeMap.keySet()) {
            if (nodeMap.get(nodeId).getNext().size() ==0){//该节点没有后置结点
                //addLink(nodeId, end);
                targetIds.add(nodeId);
            }
        }
        targetIds.forEach(t->addLink(t, end));
    }

    public boolean isFollowNodeof(String nodeId, String key) {
        //遍历图，以nodeId节点为起点。如果遇到后续节点则加入到缓存队列。
        // 第一次调用的时候填充缓存，后续直接用缓存来判断是否后续节点
        if (travelMap.get(nodeId) == null){
            synchronized (travelMap){
                if (travelMap.get(nodeId) == null){
                    travelNodeMap(nodeId);
                }
            }
        }
        return travelMap.get(nodeId).contains(key);
    }

    private void travelNodeMap(String nodeId) {
        List<String> nexts = this.getNextNode(nodeId);
        if (nexts != null && nexts.size() > 0){
            List<String> list = new ArrayList<>();
            for (String next: nexts){
                list.add(next);
                if (this.travelMap.get(nodeId) == null){
                    this.travelMap.put(nodeId , list);
                }else {
                    this.travelMap.get(nodeId).addAll(list);
                }
                this.travelNodeMap(next);
            }
        }

    }
}
