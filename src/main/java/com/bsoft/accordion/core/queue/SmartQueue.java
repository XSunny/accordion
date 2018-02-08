package com.bsoft.accordion.core.queue;


import com.bsoft.accordion.core.MetaData;
import com.bsoft.accordion.core.graph.NodeGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sky on 2018/1/26.
 *
 * 用于启动任务之后的数据获取。
 *  1.由于processor 会使用线程方式来执行，SmartQueue 需要保证线程安全。
 *  2.同一个task 内的所有processor 会共享一个 Queue, 每一个processor 只管询问Queue是否有下一个数据到达，然后，进行所需的处理，并将处理结果报告给queue。
 *  3.每一个 在queue内传递的消息都有一个batchID 与其他批次的消息进行区分，多个相同batchID会由SmartQueue进行合并，合并之后才可以被 下一个processor获取。
 *  4.为每一个processor 维护一个输入队列和输出队列，并且知道如何传递这些消息。（通过graph）
 *  5.需要一个结构来描述，每一个processor 的输出目标,告诉Queue下一传递目标，源不需要指定（由Queue 承担）
 */
public class SmartQueue implements MsgQueue {//ThreadSafe

    /**
     * 1. 存取数据
     * 2. 决定数据投递
     * 主方法 保证线程安全
     */
    private ConcurrentHashMap<String,BlockingQueue<MetaData>> metaQueue;//待处理主数据队列

    /** 等待合并的数据
    * key = batchId+nodeId,value = metaData
    * 注意，数据如果出现异常，导致部分数据无法被消费的情况，需要处理这些无法被消费的数据（防止内存泄漏），
    * 做简单的数据记录之后，可以直接移除
   */
    private ConcurrentHashMap<String,List<MetaData>> mergeQueue;

    /*
    * 事件队列数据, 进行异常处理
    */
    //private ConcurrentHashMap<String,List<MetaData>> eventQueue;

    private int DEFAULT_QUEUE_SIZE = 1000;

    private NodeGraph graph;

    private AtomicInteger batchId = new AtomicInteger();

    private boolean runningFlag = true;

    public SmartQueue(NodeGraph graph){
        this.graph = graph;
        init();
    }

    private void init() {
        metaQueue = new ConcurrentHashMap<>();
        for (String nodeId:graph.getNodeMap().keySet()) {
            BlockingQueue queue = new LinkedBlockingQueue();
            metaQueue.put(nodeId, queue);
        }
        mergeQueue = new ConcurrentHashMap<>();
    }

    public MetaData getNextMeta(String nodeId) throws InterruptedException {//默认添加一个前驱结点
        if (runningFlag && isFirstNode(nodeId) ){
            return new MetaData(batchId.getAndIncrement());
        }
        return metaQueue.get(nodeId).take();
    }

    private boolean isFirstNode(String nodeId) {
        return graph.getFirstNode().equals(nodeId);
    }

    public void pushMeta(String nodeId, MetaData meta) throws InterruptedException {
        //使用外置类获取结点之间的先序关系，并且将需要合并的数据，按照需要的方式合并。
        List<String> nexts = graph.getNextNode(nodeId);
        for(String next : nexts){
            List<String> pre  = graph.getPreNode(next);
            if (pre.size() > 1){//如果有多个来源，先merge
                String dataId = next+meta.getBatchId();
                List<MetaData> mergeData =  mergeQueue.get(dataId);
                if (mergeData == null){
                    synchronized (mergeQueue){
                        if (mergeData == null) {
                            mergeData = new ArrayList();
                            mergeQueue.put(dataId,mergeData);
                        }
                    }
                }
                synchronized (mergeData){
                    mergeData.add(meta);
                    if(mergeData.size() >= pre.size()){//如果已经是足量数据（所有数据都到达了），push，并且移除暂存队列
                        metaQueue.get(next).put(merge(mergeQueue.remove(dataId)));
                    }
                }

            }else{//不需要合并，直接push
                metaQueue.get(next).put(new MetaData(meta));
            }
        }

    }

    private MetaData merge(List<MetaData> datas) {
        MetaData meta = new MetaData();
        datas.forEach(meta::merge);
        return meta;
    }

    public void cleanUp(String nodeId, MetaData sourceMeta) {
        mergeQueue.remove(nodeId+sourceMeta.getBatchId());
    }

    @Override
    public void sendEndMsg(String nodeId){
        MetaData end = new MetaData();
        end.setEndMsg(true);
        runningFlag = false;
        for (String key :metaQueue.keySet()) {
            try {
                metaQueue.get(key).put(end);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public NodeGraph getGraph() {
        return graph;
    }

    public void setGraph(NodeGraph graph) {
        this.graph = graph;
    }


}
