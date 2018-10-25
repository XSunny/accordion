package com.bsoft.rpc;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sky on 2018/6/20.
 */
public class DefaultRPC implements RPCCall{

    @Override
    public <T> T getService(Class<?> interfaceName, URL url) {
        //1. link to register service;
        //2. get target service url, and cache it.
        //3. link to the remote service, and check it available
        //4. create proxy to link the service and return
        return null;
    }

    @Override
    public <T> void exportService(Class<?> interfaceName, URL url,T impl) {
        //1. start a server to wait for link.
        //2. link the impl to the server
        //3. register the url.

    }


    public static void main(String [] args){
        try {
            URL url = new URL("http://hostname:8090/path1/?params=1");
            System.out.println(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
