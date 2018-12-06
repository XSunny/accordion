package com.bsoft.net;

import com.bsoft.util.HttpRequestUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.*;
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

        ExecutorService threads = Executors.newFixedThreadPool(30);
        long times = 100;
        long time = System.currentTimeMillis();
        for (int i = 0; i < times; i++){
            threads.submit(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<>();
                    params.put("empiid", "7ae7d70a286748608eb474f46b38fec6");
                    params.put("recordcode", "wm");
                    params.put("drugcode", "908");
                    String content= HttpRequestUtil.httpRequestPost("http://localhost:8080/BHRView/drugsRecord/getRecordList.do"
                            ,"JSESSIONID=892d76fa-2bcf-4555-930e-b842044ad723; tmp0=eNpdUE1PwkAQDV9CNEa9ejLEk8Fm2y2UciOKHwkWI3rxotvuFBZ3u7C7lA%2Fjz%2FF%2FusV4cQ4zk5k3773M3UGyVAoy86JBXaHLRAqHcBaTmDh0GcfSUTBh2qiNY1dCZg6VgrDMKfDfjaXNGRHQmBOtV1LRqpIc6okCYqSqQ0ZiDrRaQPYpzIkywmrVwFLw2nwqM6hbNUP4xx6XCeFwbAlzlsCjYjnjMIEyow05B1XwHf01fUoVaF0VTOuT%2Fxe6wqj%2BtQC0ISRlKQNayeTq3bqTBp2B63penGIaQteNvSBNwwD7OO54adDBabv0FD3voJGN8nZaurD1ZnE6IzlxloZxp68U2ejzXRna9%2BwQ0eF4MB7fj6K3h9H1YIhuPxntNV3qb1dYyFmwQFOVmxCFSIbdYN1sJabntl0Xe2Hb9f0AtUhSDHDghwhhHIQtse6hr9cf7aiNkw%3D%3D; splashShown1.6=1; Idea-667fe80f=4fb37348-2ca9-4b74-bb50-8bdb8227aa69; NG_TRANSLATE_LANG_KEY=zh-CN; ajs_group_id=null; ajs_anonymous_id=%22e88aa9ba7f9c4a0a39dad2b3d8fafa5a%22; ajs_user_id=%22e88aa9ba7f9c4a0a39dad2b3d8fafa5a%22; _ga=GA1.1.655302247.1525850047; intercom-lou-bda97f31bea8f639d9e93721d781cef31ff00628=1; JSESSIONID=1kzg7uaxvz8b81ck2n14xtzop1; BD_UPN=12314753",
                            "",params
                            );

                    System.out.println(content);
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
        System.out.println("avg times : " + (used/times) + " ms");
    }
}
