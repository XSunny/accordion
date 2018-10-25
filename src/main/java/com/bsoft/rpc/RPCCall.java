package com.bsoft.rpc;
import java.net.URL;

/**
 * Created by sky on 2018/6/20.
 */
public interface RPCCall {

    public <T> T getService(Class<?> interfaceName, URL url);

    public <T> void exportService(Class<?> interfaceName, URL url, T impl);
}
