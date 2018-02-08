package com.bsoft.accordion.core.task;

import com.bsoft.accordion.core.factory.NodeFactory;
import com.bsoft.accordion.core.node.Config;
import com.bsoft.accordion.core.process.Processor;

/**
 * Created by sky on 2018/2/7.
 */
public class NodeMeta {

    private String nodeId;

    private NodeFactory.TYPE nodeType;

    private Config config;

    private Processor processor;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeFactory.TYPE getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeFactory.TYPE nodeType) {
        this.nodeType = nodeType;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
}
