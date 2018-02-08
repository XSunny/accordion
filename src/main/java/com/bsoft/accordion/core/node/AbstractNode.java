package com.bsoft.accordion.core.node;

import com.bsoft.accordion.core.MetaData;
import com.bsoft.accordion.core.process.DefaultProcessor;
import com.bsoft.accordion.core.process.Processor;
import com.bsoft.accordion.core.queue.MsgQueue;
import com.bsoft.accordion.core.queue.QueueManager;

/**
 * Created by sky on 2018/1/25.
 */
public abstract class AbstractNode implements Runnable{

    private String taskId;//统一任务id

    protected String nodeId;//节点在 graph 和 queue 中的唯一标识

    protected Processor processor;

    protected Config config;// 取消了节点持有消息的机制而引入了配置

    protected volatile Boolean runningFlag = true;

    protected Boolean firstNode = false;//是否是第一个起始节点

    protected boolean skipable = false;//是否跳过错误数据

    public AbstractNode(){
        processor = new DefaultProcessor();
    }

    public AbstractNode(Processor processor){
        this.processor = processor;
    }

    public void run(){

        while (getRunningFlag()) {//线程起停标志
            MetaData sourceMeta = null;
            MsgQueue queue = QueueManager.getQueue(taskId);
            try {
                if (queue == null){
                    throw new NullPointerException("none data queue found, init error!");
                }
                sourceMeta =  queue.getNextMeta(nodeId);//TODO 交由任务层面控制新建数据,当前问题：过量生成起始消息
                if (sourceMeta == null){
                    throw new NullPointerException("source meta data is null");
                }
                if (sourceMeta.isEndMsg()){//停止消息
                    runningFlag = false;
                    System.out.println("**{ end }node exit id :"+this.nodeId);
                    break;
//                    queue.pushMeta(nodeId, sourceMeta);//* 向下传递停止消息方案被抛弃的原因是 ： 如果节点不在出错节点的下游，将接收不到这个停止消息，前置节点仍然无法保证一致性问题。
                }
                MetaData meta = processor.process(sourceMeta);
                queue.pushMeta(nodeId, meta);
//                if (meta.isEndMsg()){
//                    runningFlag = false;
//                    break;
//                }
            }catch (Exception e){

                e.printStackTrace();//记录异常
//                event.saveEvent(sourceMeta, meta, nodeId, e);

                if (queue != null){//清理本次batch id 的 数据，防止错误向下传递
                    queue.cleanUp(nodeId, sourceMeta);
                }

                if (!skipable){//如果错误无法跳过，停止本处理过程，并且发送整个任务失败消息。
                    queue.sendEndMsg(nodeId);
                    runningFlag = false;
                }
            }
        }
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public boolean getRunningFlag() {
        return runningFlag;
    }

    public void setRunningFlag(Boolean flag){
        this.runningFlag = flag;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
