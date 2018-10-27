package com.bsoft.net;

import com.bsoft.util.HttpRequestUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by sky on 2018/10/26.
 */
public class HttpHandlerProcess implements Runnable{

    private ChannelHandlerContext ctx;

    private FullHttpRequest request;

    private String contentType = "text/html";


    public HttpHandlerProcess(ChannelHandlerContext ctx, FullHttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    @Override
    public void run() {
        //1.获得请求源地址 并转换
//        System.out.println(request.toString());
        //2.发起请求

        //3/返回请求

        //getRealResponse():
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(HttpRequestUtil.httpRequest("http://10.10.0.207:9392").getBytes())); // 2
        //HttpRequestUtil.httpRequest("http://www.baidu.com");

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
        ctx.flush();
        System.out.println("----- *** ----- thread process finished.");


    }

    public static void main(String []args){

        ExecutorService threads = Executors.newFixedThreadPool(100);
        long times = 1000;
        long time = System.currentTimeMillis();
        for (int i = 0; i < times; i++){
            threads.submit(new Runnable() {
                @Override
                public void run() {
                    HttpRequestUtil.httpRequest("http://localhost:9392/");
                }
            });
        }
        threads.shutdown();

        try
        {
            // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
            while (!threads.awaitTermination(10, TimeUnit.SECONDS));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        long used = System.currentTimeMillis() - time;
        System.out.println("time used : " + (used/1000.0) + " s");
        System.out.println("TPS : " + (times*1000/used) + " tps");
    }
}
