package com.bsoft.accordion.core.node;

/**
 * Created by sky on 2018/2/2.
 *
 * 主要用来统计通过数据，并且统一数据传递模型。
 * 每一个task 都会有一个 标识为 start 的全局前置节点，和一个表示为end 的全局后置节点。
 *
 */
public class Endpoint extends AbstractNode {
    public Endpoint(String s) {
        this(s, null);
    }

    public Endpoint(String s, String taskId) {
        this.setTaskId(taskId);
        this.nodeId =s;
    }
}
