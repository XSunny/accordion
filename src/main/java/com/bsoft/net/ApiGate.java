package com.bsoft.net;

/**
 * Created by sky on 2018/10/27.
 */
public class ApiGate {

    public ApiGate(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                NettyServer.start();
            }
        });
        t.start();
    }
}
