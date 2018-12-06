package com.bsoft.net;


import com.bsoft.db.RecordWrapper;
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
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // 1

    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    static private final ExecutorService excutor = Executors.newFixedThreadPool(100);

    private String HOST_NAME = "HOST";

    private String RES_CODE_FAILED = "failed.";
    private String RES_CODE_OK = "success.";
    private String SYSTEM_ERROR = "server inner error.";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        Watch watch = Watch.New().start();
        processHeader(ctx, msg);
        System.out.println("process header cost : " + watch.end());
    }

    @Deprecated
    private void saveLog(ChannelHandlerContext ctx, FullHttpRequest msg, String matchUrl, String result, long timeCost, String cause){
        try {// ensure safe
            RecordWrapper logs = new RecordWrapper("id", UUID.randomUUID().toString())
                    .set("id", UUID.randomUUID().toString())
                    .set("ipaddress", ctx.channel().remoteAddress())
                    .set("url", msg.uri())
                    .set("matchUrl", matchUrl)
                    .set("content", msg.content().toString(CharsetUtil.UTF_8))
                    .set("requestType", msg.method().asciiName())
                    .set("requestTime", new Date())
                    .set("timeCost", timeCost)
                    .set("result", result)
                    .set("resultDesp", cause);
            MsgLogger.save(logs.toMap());
        }catch (Exception e){

        }

    }

    private void saveLog(String  ip, String url, String content, String method, String matchUrl, String result, long timeCost, String cause){
        try {// ensure safe
            RecordWrapper logs = new RecordWrapper("id", UUID.randomUUID().toString())
                    .set("id", UUID.randomUUID().toString())
                    .set("ipaddress", ip)
                    .set("url", url)
                    .set("matchUrl", matchUrl)
                    .set("content", content)
                    .set("requestType", method)
                    .set("requestTime", new Date())
                    .set("timeCost", timeCost)
                    .set("result", result)
                    .set("resultDesp", cause);
            MsgLogger.save(logs.toMap());
        }catch (Exception e){

        }
    }

    private void processHeader(ChannelHandlerContext ctx, FullHttpRequest msg) {

        Watch watch = Watch.New().start();
        String url = msg.uri();
        //url = getRegistUrl(url);
        Pair<String,String> url_match = Registry.getMatchUrl(url);
        String hostname = msg.headers().get(HOST_NAME);
        //get contents
        String contentIn = msg.content().toString(CharsetUtil.UTF_8);
        String method = msg.method().asciiName().toString();
        Map<String, String> headers = new HashMap<>();
        for (Map.Entry entry : msg.headers().entries()){
            headers.put(entry.getKey().toString(), entry.getValue().toString());
        }
        String ip = ctx.channel().remoteAddress().toString();

        //send request to the real server
        excutor.submit(new Runnable() {
            @Override
            public void run() {
                HttpResponse response = HttpClient.requestServer(url_match.getValue(), "",
                        method, contentIn, headers);

                if (response == null){
                    DefaultFullHttpResponse response_t = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                            HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer("request failed.".getBytes()));
                    ctx.write(response_t);
                    ctx.flush();
                    saveLog(ip, url, contentIn,  method, url_match.getKey(), RES_CODE_FAILED, watch.end().timeCost(), "url: "+url_match.getValue()+" 访问失败" );
                    return;
                }
                //get remote ip addr
                //System.out.println(ctx.channel().remoteAddress());

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
                saveLog(ip, url, contentIn,  method, url_match.getKey(), RES_CODE_OK, watch.end().timeCost(), "");
            }
        });


    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        System.out.println("exceptionCaught");
        saveLog(ctx.channel().remoteAddress().toString(), "", "", "", "", SYSTEM_ERROR, -1, cause.toString() );
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}
