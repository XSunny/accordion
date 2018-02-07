package com.bsoft.accordion.core.queue;


import com.bsoft.accordion.core.graph.NodeGraph;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sky on 2018/1/29.
 */
public class QueueManager {


    private ConcurrentHashMap<String, SmartQueue> queues = new ConcurrentHashMap();

    public static SmartQueue getQueue(String taskId) {
        return getInstance().getQueueById(taskId);
    }

    private QueueManager(){}

    public static QueueManager getInstance(){
        return HolderClass.instance;
    }

    private static class HolderClass{
        final static QueueManager instance = new QueueManager();
    }

    public ConcurrentHashMap<String, SmartQueue> getQueues() {
        return queues;
    }

    public SmartQueue getQueueById(String taskId){
        SmartQueue squeue = queues.get(taskId);
        return squeue;
    }

    public SmartQueue  CreateNewQueue(String taskId, NodeGraph graph){//保守策略，每一个taskId绑定一个图，同一id先绑定的为准
        if (queues.get(taskId) == null){
            synchronized (queues){
                if (queues.get(taskId) == null){
                    SmartQueue queue = new SmartQueue(graph);
                    queues.put(taskId, queue);
                }
            }
        }
        return  queues.get(taskId);
    }
}
