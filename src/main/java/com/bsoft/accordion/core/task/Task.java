package com.bsoft.accordion.core.task;


import com.bsoft.accordion.core.graph.NodeGraph;
import com.bsoft.accordion.core.metadata.MetaData;
import com.bsoft.accordion.core.node.AbstractNode;
import com.bsoft.accordion.core.proxy.ProxyCenter;
import com.bsoft.accordion.core.proxy.Wrapper;
import com.bsoft.accordion.core.queue.QueueManager;
import com.bsoft.accordion.core.queue.SmartQueue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sky on 2018/1/25.
 *
 * 初始化顺序：
 *  1）先得到一个taskId，所有的node都会使用这个id进行初始化。
 *  2）配置好每一个node，生成nodeId 来标识node
 *  3）将node间的数据关系用graph构建好，使用nodeId来定位和标识
 *  4）将配置好的关系图graph提供给smartQueue进行初始化，用taskId标识。
 *
 *   完成上面步骤之后，任务达到可执行状态，将所有node提交给线程池即可
 */
public abstract class Task{// 不需要实现Runnable,直接实现

    protected Task (String taskId, List<AbstractNode> nodes, NodeGraph graph){
        this.taskId= taskId;
        this.chain = nodes;
        setGraph(graph);
    }
    protected String taskId;

    protected List<AbstractNode> chain;//所有已经链接好的节点集合

    protected NodeGraph graph;//所有的节点连接关系

    protected SmartQueue queue;//所有节点共享的逻辑消息队列

    private ExecutorService service = Executors.newCachedThreadPool();//应该修改为全局线程池？

    public void excute() {
        for (AbstractNode link: chain) {
            service.submit(ProxyCenter.getProxy(Runnable.class, link, new Wrapper() {
                long watch;

                @Override
                public Object beforeMethod(Object obj, Method method, Object[] args) {
                    watch = System.currentTimeMillis();
                    return null;                }

                @Override
                public Object afterMethod(Object obj, Method method, Object[] args) {
                    MetaData meta = (MetaData) args[0];
                    //System.out.println("process time cost: "+ (System.currentTimeMillis()-watch));
                    System.out.println("process speed: "+meta.getBatchId()/(System.currentTimeMillis()-watch));
                    return null;
                }


            }));
        }
        service.shutdown();
    }

    @Deprecated
    public void stopAll(){
        for (AbstractNode node : chain){
            node.setRunningFlag(false);
        }
    }

    public void setGraph(NodeGraph graph){
        this.graph = graph;
        if (graph != null){
            this.queue = QueueManager.getInstance().CreateNewQueue(taskId, graph);
        }
    }

    public List<AbstractNode> getChain() {
        return chain;
    }

    public void setChain(List<AbstractNode> chain) {
        this.chain = chain;
    }
}
