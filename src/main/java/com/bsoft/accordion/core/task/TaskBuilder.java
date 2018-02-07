package com.bsoft.accordion.core.task;


import com.bsoft.accordion.core.MetaData;
import com.bsoft.accordion.core.graph.NodeGraph;
import com.bsoft.accordion.core.node.AbstractNode;
import com.bsoft.accordion.core.node.DataInputNode;
import com.bsoft.accordion.core.node.Endpoint;
import com.bsoft.accordion.core.node.NormalNode;
import com.bsoft.accordion.core.process.Processor;

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

    public static Task createTask(){

        String taskId = UUID.randomUUID().toString();
        Task task = new DefaultTask(taskId);

        AbstractNode dataOutputNode = new DataInputNode();
        dataOutputNode.setProcessor(new Processor() {
            int count =0;
            @Override
            public MetaData process(MetaData data) {
                data.getData().put("datacount", count++);
                data.getData().put("normalData", "name1");
                System.out.println("Intput 0 meta:" + data.getData());
                if (count > 100){
                    throw new NullPointerException();
                }
                return data;
            }

            private MetaData endMsg() {
                MetaData meta = new MetaData();
                meta.setEndMsg(true);
                return meta;
            }
        });
        dataOutputNode.setTaskId(taskId);
        dataOutputNode.setNodeId("inputNode#1");

        AbstractNode dataOutputNode1 = new NormalNode();
        dataOutputNode1.setProcessor(new Processor() {

            @Override
            public MetaData process(MetaData data) {
                int num = (Integer) data.getData().get("datacount")- 1;
                data.getData().put("addData1", num);
                System.out.println("normalnode 1 excute! meta:"+data.getData());
                return data;
            }
        });
        dataOutputNode1.setTaskId(taskId);
        dataOutputNode1.setNodeId("normalNode#1");

        AbstractNode dataOutputNode2 = new NormalNode();
        dataOutputNode2.setProcessor(new Processor() {

            @Override
            public MetaData process(MetaData data) {
                int num = (Integer) data.getData().get("datacount")+ 1;
                data.getData().put("addData2", num);
                System.out.println("normalnode 2 excute! meta:"+data.getData());
                return data;
            }
        });
        dataOutputNode2.setTaskId(taskId);
        dataOutputNode2.setNodeId("normalNode#2");

        AbstractNode dataOutputNode3 = new NormalNode();
        dataOutputNode3.setProcessor(new Processor() {

            int i = 0;
            @Override
            public MetaData process(MetaData data) {
                int sub = (Integer) data.getData().get("addData1")+ (Integer) data.getData().get("addData2");
                data.getData().put("sub", sub);
                System.out.println("sub node excute! meta:"+data.getData());
                return data;
            }
        });
        dataOutputNode3.setTaskId(taskId);
        dataOutputNode3.setNodeId("normalNode#3");

        task.chain.add(dataOutputNode);
        task.chain.add(dataOutputNode1);
        task.chain.add(dataOutputNode2);
        task.chain.add(dataOutputNode3);

        NodeGraph graph = new NodeGraph();
        graph.addLink(dataOutputNode, dataOutputNode1);
        graph.addLink(dataOutputNode, dataOutputNode2);
        graph.addLink(dataOutputNode1, dataOutputNode3);
        graph.addLink(dataOutputNode2, dataOutputNode3);


        //Set first  and end node;
        //Complete ();
        AbstractNode head = new Endpoint("Start#0", taskId);
        AbstractNode end = new Endpoint("End#0", taskId);
        graph.setFistNode(head);
        graph.setEndNoe(end);

        task.chain.add(head);
        task.chain.add(end);

        task.setGraph(graph);
        return task;
    }
}
