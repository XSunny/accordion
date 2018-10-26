package com.bsoft.net;


import com.bsoft.util.HttpRequestUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by sky on 2018/10/26.
 */


/**
 * Created by RoyDeng on 17/7/20.
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    private String HOST_NAME = "HOST";

    private String RES_CODE_FAILED = "failed.";
    private String RES_CODE_OK = "success.";

    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
//        System.out.println("class:" + msg.getClass().getName());
//        System.out.println(msg.toString());
//        System.out.println(msg.content().toString(CharsetUtil.UTF_8));


        Watch watch = Watch.New().start();
        processHeader(ctx, msg);
        System.out.println("process header cost : " + watch.end());
    }

    private void saveLog(ChannelHandlerContext ctx, FullHttpRequest msg, String result){
        Map<String, Object> logs = new HashMap<>();
        logs.put("ipaddress", ctx.channel().remoteAddress());
        logs.put("", "");
        logs.put("", "");
        logs.put("", "");
        logs.put("", "");
        logs.put("", "");
        logs.put("", "");
//        MsgLogger.save(logs);
    }

    private void processHeader(ChannelHandlerContext ctx, FullHttpRequest msg) {

        String url = msg.uri();
        url = getRegistUrl(url);
        //String hostname = msg.headers().get(HOST_NAME);

        //send request to the real server
        HttpResponse response = send2Server(url, "", msg);

        if (response == null){
            DefaultFullHttpResponse response_t = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer("".getBytes()));
            ctx.write(response_t);
            ctx.flush();
            saveLog(ctx, msg, RES_CODE_FAILED);
            return;
        }

        //get remote ip addr
        System.out.println(ctx.channel().remoteAddress());

        //construct the response
        String content = "";
        try {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DefaultFullHttpResponse response_t = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
        HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes())); // 2
        for (Header header :response.getAllHeaders()) {
            response_t.headers().add(header.getName(), header.getValue());
        }

        //send back
        ctx.write(response_t);
        ctx.flush();
        saveLog(ctx, msg, RES_CODE_OK);
    }

    private String getRegistUrl(String url) {
        String url_r = Registry.getRealUrl(url);
        if (url_r == null){
            return url;
        }
        return url_r;
    }

    private HttpResponse send2Server(String url, String hostname, FullHttpRequest msg) {
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpUriRequest requst = null;
        switch (msg.getMethod().asciiName().toString().toUpperCase()){//method
            case "GET":{
                requst = new HttpGet(url);

            }break;
            case "POST":{
                requst = new HttpPost(url);
                try {
                    ((HttpPost)requst).setEntity(new StringEntity(msg.content().toString(CharsetUtil.UTF_8)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }break;
            default:
                return null;
        }

        //headers
        for (Map.Entry header :msg.headers().entries()){
            if (!header.getKey().toString().toLowerCase().equals("content-length")&&!header.getKey().toString().toLowerCase().equals("host"))
                requst.addHeader(header.getKey().toString(), header.getValue().toString());
        }
        try {//send
            HttpResponse httpResponse = closeableHttpClient.execute(requst);
            String content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            httpResponse.setEntity(new StringEntity(content));
            if (httpResponse != null) {
                return httpResponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
