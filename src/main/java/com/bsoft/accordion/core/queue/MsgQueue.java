package com.bsoft.accordion.core.queue;


import com.bsoft.accordion.core.metadata.MetaData;

/**
 * Created by sky on 2018/2/1.
 */
public interface MsgQueue {

    public MetaData getNextMeta(String nodeId) throws InterruptedException;

    public void pushMeta(String nodeId, MetaData meta) throws InterruptedException;

    void cleanUp(String nodeId, MetaData sourceMeta);

    void sendEndMsg(String nodeId);
}
