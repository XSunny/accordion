package com.bsoft.accordion.core.node;

import java.util.*;

/**
 * Created by sky on 2018/2/8.
 *
 *   配置信息类，包装了一个map
 *  TODO 需要考虑多线程情况吗？
 *
 */
public class Config {

    private Map<String, Object> config;

    public Map<String, Object> getConfig() {
        return Collections.unmodifiableMap(config);
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public Object get(String key){
        return config.get(key);
    }

    public void set(String key, Object obj){
        this.config.put(key, obj);
    }
}
