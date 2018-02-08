package com.bsoft.accordion.core.task;


import com.bsoft.accordion.core.factory.NodeFactory;
import com.bsoft.accordion.core.graph.NodeGraph;
import com.bsoft.accordion.core.node.AbstractNode;
import com.bsoft.accordion.core.node.DataInputNode;
import com.bsoft.accordion.core.node.Endpoint;
import com.bsoft.accordion.core.process.Processor;
import java.util.List;
import java.util.UUID;

/**
 * Created by sky on 2018/1/25.
 *
 *  用来生成基于时间参数的 数据比对任务
 *
 *  Factory.
 *
 *  已经具备的功能：
 *  1. 使用不同的 processor 来定义具体处理过程，并且指定不同的processor的数据先后关系之后，可以按照既定流程执行数据处理，
 *  数据会按照规定的顺序在不同的processor中传递，不同的processor运行在不同的线程之中，甚至可以运行在不同的进程之中（提供了分布式的可能性），但是需要实现 跨jvm版本的SmartQueue.
 *  2. 使用局部加锁和线程安全类保证共享数据的线程安全性，其中，在节点内传递的meta 采用浅拷贝方式， 其中用于保存数据的map 使用构造函数重建，
 *  但是map中的元素可能会有共享问题,故，建议放入meta中的对象都是不可变对象。
 *
 *  可能需要继续深入的点：
 *  TODO  1.task 并没有一个高层的起停控制，需要依赖编码修改meta来进行控制，不能根据某个出错的异常来决定是否停止task。
 *  --计划使用 queue 给每个node发送一个停止消息来实现。
 *  已完成
 *
 *  TODO　2.作为核心的Queue抽象程度不够，以后扩展为分布式的queue会有问题。
 *  --计划使用接口来隔离实现
 *  接口抽象完成，现在每一个执行节点的全局统一 nodeId 成为分布式执行的关键。
 *
 *  TODO  3.graph 和 task的创建稍显麻烦，最好还有 节点的 id 统一控制
 *  --计划使用封装来优化创建
 *
 *  TODO 4.没有进行数据统计，提供作业执行的运行情况说明
 *
 *  TODO 5.事务控制，分布式一致性问题。（最好保证每个操作等幂，否则采用互相隔离的线程方案本来就会有分布式数据一致性问题。）
 *
 */
public class TaskBuilder {


    /*
     input :
      node list:
            node  id
            node type
            processor
      links list:
            node link

     */
    public static Task createTask(List<NodeMeta> nodes, List<Link> links){

        String taskId = UUID.randomUUID().toString();
        Task task = new DefaultTask(taskId);

        nodes.forEach(node -> addNode(task, taskId, node));

        NodeGraph graph = new NodeGraph();
        links.forEach(link -> graph.addLink(link.getHead(), link.getDirection()));

        //Set first  and end node;
        //Complete ();
        complete(taskId, task, graph);

        task.setGraph(graph);

        return task;
    }

    private static void addNode(Task task, String taskId, NodeMeta nodeMeta) {
        AbstractNode node = NodeFactory.getProcessNode(nodeMeta.getNodeType(), nodeMeta.getConfig());
        node.setProcessor(nodeMeta.getProcessor());
        node.setTaskId(taskId);
        node.setNodeId(nodeMeta.getNodeId());
        task.chain.add(node);
    }

    private static void complete(String taskId, Task task, NodeGraph graph) {
        AbstractNode head = new Endpoint("Start#0", taskId);
        AbstractNode end = new Endpoint("End#0", taskId);
        graph.setFistNode(head);
        graph.setEndNoe(end);
        task.chain.add(head);
        task.chain.add(end);
    }

    public static AbstractNode createAbstractNode(Task task , String taskId, String nodeId, String nodeType, Processor processor) {
        AbstractNode node = new DataInputNode();
        node.setProcessor(processor);
        node.setTaskId(taskId);
        node.setNodeId("inputNode#1");
        task.chain.add(node);
        return node;
    }
}
