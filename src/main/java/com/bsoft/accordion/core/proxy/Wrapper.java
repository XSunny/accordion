package com.bsoft.accordion.core.proxy;

import java.lang.reflect.Method;

/**
 * Created by sky on 2018/4/28.
 */
public interface Wrapper {

    Object beforeMethod(Object obj, Method method, Object[] args);

    Object afterMethod(Object obj, Method method, Object[] args);
}
