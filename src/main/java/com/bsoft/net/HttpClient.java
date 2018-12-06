package com.bsoft.net;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sky on 2018/10/26.
 */
public class HttpClient {

    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

    private static final int timeout = 60 * 1000;

    static {
        // Increase max total connection to 50
        connManager.setMaxTotal(50);
        // Increase default max connection per route to 20
        connManager.setDefaultMaxPerRoute(50);

        // Increase max connections for localhost:80 to 50
//        HttpHost localhost = new HttpHost("locahost", 80);
//        connManager.setMaxPerRoute(new HttpRoute(localhost), 50);

        Thread thread = new IdleConnectionMonitorThread(connManager);
        thread.start();
    }

    private static final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//    static CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

    static CloseableHttpClient  closeableHttpClient = HttpClients.custom()
            .setConnectionManager(connManager)
                .setDefaultRequestConfig(RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build())
            .build();


    static {

        // 设置最大连接数
        connManager.setMaxTotal(200);
        // 设置每个连接的路由数
        connManager.setDefaultMaxPerRoute(20);

    }

    public static void close(){
        connManager.shutdown();
        try {
                closeableHttpClient.close();
        } catch (IOException e) {
        }
    }

    public static HttpResponse requestServer(String url, String hostname, String method, String inContent, Map<String,String> headers) {


//        CloseableHttpClient closeableHttpClient = HttpClients.custom()
//                .setConnectionManager(connManager)
//                .build();


        HttpUriRequest requst = null;
        switch (method.toString().toUpperCase()){//method
            case "GET":{
                requst = new HttpGet(url);

            }break;
            case "POST":{
                requst = new HttpPost(url);
                ((HttpPost)requst).setEntity(new StringEntity(inContent, Charset.forName("UTF-8")));
            }break;
            default:
                return null;
        }

        //headers

        for (Map.Entry header :headers.entrySet()){
            if (!header.getKey().toString().toLowerCase().equals("content-length")&&!header.getKey().toString().toLowerCase().equals("host"))
                requst.addHeader(header.getKey().toString(), header.getValue().toString());
        }
        try {//send
            HttpResponse httpResponse = closeableHttpClient.execute(requst);
            String content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            httpResponse.setEntity(new StringEntity(content, Charset.forName("UTF-8")));
            if (httpResponse != null) {
                return httpResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                closeableHttpClient.close();
//            } catch (IOException e) {
//            }
        }
        return null;
    }


    /**
     * 获取Http客户端连接对象
     *
     * @param timeOut 超时时间
     * @return Http客户端连接对象
     */
    public static CloseableHttpClient getHttpClient(Integer timeOut) {
        // 创建Http请求配置参数
        RequestConfig requestConfig = RequestConfig.custom()
                // 获取连接超时时间
                .setConnectionRequestTimeout(timeOut)
                // 请求超时时间
                .setConnectTimeout(timeOut)
                // 响应超时时间
                .setSocketTimeout(timeOut)
                .build();

        /**
         * 测出超时重试机制为了防止超时不生效而设置
         *  如果直接放回false,不重试
         *  这里会根据情况进行判断是否重试
         */
        HttpRequestRetryHandler retry = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        // 创建httpClient
        return HttpClients.custom()
                // 把请求相关的超时信息设置到连接客户端
                .setDefaultRequestConfig(requestConfig)
                // 把请求重试设置到连接客户端
                .setRetryHandler(retry)
                // 配置连接池管理对象
                .setConnectionManager(connManager)
                .build();
    }
}
