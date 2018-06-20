package com.bsoft.accordion.core.metadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sky on 2018/1/25.
 *
 *  在步骤中传递的消息节点之间传递，传递方式浅拷贝
 */
public class MetaData {

    private Map<String,Object> data = new HashMap();

    private long batchId = 0;

    private boolean endMsg = false;

    public MetaData(){}

    public MetaData(long batchId){
        this.batchId = batchId;
    }

    public MetaData(MetaData meta) {
        this.batchId = meta.batchId;
        this.endMsg = meta.endMsg;
        this.data = new HashMap(meta.data);
    }

    public void merge(MetaData meta) {
        this.endMsg = meta.endMsg || this.endMsg;
        data.putAll(meta.getData());
    }

    private Map<String, Object> getData(){
        return this.data;
    }

    public Map<String, Object> getDataAsMap() {// read only
        return Collections.unmodifiableMap(data);
    }

    public void set(String key, Object obj){
        this.data.put(key, obj);
    }

    public Object get(String key){
        return this.data.get(key);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public boolean isEndMsg() {
        return endMsg;
    }

    public void setEndMsg(boolean endMsg) {
        this.endMsg = endMsg;
    }
}
