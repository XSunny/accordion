package com.bsoft.accordion.core.proxy;

import java.lang.reflect.Method;

import static java.lang.System.currentTimeMillis;

/**
 * Created by sky on 2018/5/25.
 */
public class TimerWrapper implements Wrapper{

    private long timestamp;

    @Override
    public Object beforeMethod(Object obj, Method method, Object[] args) {
        timestamp = currentTimeMillis();
        System.out.println("before time stamp:" + timestamp);
        return null;
    }

    @Override
    public Object afterMethod(Object obj, Method method, Object[] args) {
        System.out.println("time cose:" + (currentTimeMillis() - timestamp));
        return null;
    }
}
