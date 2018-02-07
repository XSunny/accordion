package com.bsoft.accordion.core.task;


import com.bsoft.accordion.core.graph.NodeGraph;
import com.bsoft.accordion.core.node.AbstractNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2018/1/31.
 */
public class DefaultTask extends Task {

    protected DefaultTask(String taskId, List<AbstractNode> nodes, NodeGraph graph) {
        super(taskId, nodes, graph);
    }

    public DefaultTask(String taskId) {
        super(taskId, new ArrayList<>(), null);
    }
}
