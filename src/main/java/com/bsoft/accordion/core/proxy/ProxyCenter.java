package com.bsoft.accordion.core.proxy;

import com.bsoft.accordion.core.compare.ResultProcessor;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sky on 2018/4/28.
 */
public class ProxyCenter{

    public static <T> T getProxy(Class<T> t, Object realObject) {
        return  getProxy(t, new Class[]{t}, null);
    }

    public static <T> T getProxy(Class<T> t, Object realObject, Wrapper wrapper) {
        return (T) Proxy.newProxyInstance(t.getClassLoader(), new Class[]{t}, new InnerProxy(realObject, wrapper));
    }

    public static Object getProxy(Object realObject, Wrapper wrapper) {
        return Proxy.newProxyInstance(realObject.getClass().getClassLoader(), realObject.getClass().getInterfaces(), new InnerProxy(realObject, wrapper));
    }


    static class InnerProxy implements InvocationHandler{

        Wrapper wrapper = new Wrapper() {
            @Override
            public Object beforeMethod(Object obj, Method method, Object[] args) {
                //                System.out.print("before method, args: ");
//                for (Object arg: args)
//                    System.out.print(arg +" , ");
//                System.out.println();
                return null;
            }

            @Override
            public Object afterMethod(Object obj, Method method, Object[] args) {
                //                System.out.print("after method, args: ");
//                for (Object arg: args)
//                    System.out.print(arg +" , ");
//                System.out.println();
                return null;
            }
        };

        Object object;

        InnerProxy(){}

        InnerProxy(Object object){
            this.object = object;
        }

        InnerProxy(Object object, Wrapper wrapper) {
            if (wrapper != null){
                this.wrapper = wrapper;
            }
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            wrapper.beforeMethod(object, method, args);
            Object o = method.invoke(object, args);
            wrapper.afterMethod(object, method, args);
            return o;
        }
    }

}
