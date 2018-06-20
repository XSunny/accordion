package com.bsoft.accordion.core.compare;

import java.util.*;
/**
 * Created by sky on 2018/4/18.
 */
public class CheckResult {

    public CheckResult(){}

    public CheckResult(boolean result){
        this.set("countEquals", result);
    }

    private Map<String, Object> entry = new HashMap<>();

    public CheckResult(boolean result, String message) {
        this.set("countEquals", result);
        this.set("msg", message);
    }

    public Object get(String key){
        return entry.get(key);
    }

    public void set(String key, Object o){
        this.entry.put(key, o);
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "entry=" + entry +
                '}';
    }

    public void setResult(boolean result) {
        this.set("countEquals", result);
    }
}
