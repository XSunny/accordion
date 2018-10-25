package com.bsoft.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;

import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;

/**
 * Created by sky on 2018/6/20.
 */
public class NioServer extends ChannelInboundHandlerAdapter {

    static String str = "HTTP/1.1 200 OK\n" +
            "Date: Thu, 25 Oct 2018 09:10:04 GMT\n" +
            "Server: Apache/2.4.18 (Ubuntu)\n" +
            "Last-Modified: Thu, 19 Mar 2015 13:04:05 GMT\n" +
            "ETag: \"7893-511a3d5ad8c36-gzip\"\n" +
            "Accept-Ranges: bytes\n" +
            "Vary: Accept-Encoding\n" +
            "Content-Encoding: gzip\n" +
            "Content-Length: 7\n" +
            "Keep-Alive: timeout=30, max=100\n" +
            "Connection: Keep-Alive\n" +
            "Content-Type: text/html\n 1111111";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) { // (1)
//                System.out.print((char) in.readByte());
//                System.out.flush();
//
//            }


        byte[] data = new byte[((ByteBuf) msg).capacity()];
        for (int i = 0; i< ((ByteBuf) msg).capacity(); i++){
            data[i] = ((ByteBuf) msg).readByte();
        }
        ((ByteBuf) msg).clear();
        StringEntity entry = null;
        try {
            String request = new String(data);
            entry = new StringEntity(request);
            System.out.println(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(entry);


        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        HttpEntity myEntity1 = null;
        try {
            myEntity1 = new StringEntity(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setEntity(myEntity1);

        ByteBuf resp = ctx.alloc().buffer(response.toString().length());
        resp.writeBytes(response.toString().getBytes());

        ctx.write(resp); // (1)
        ctx.flush(); // (2)
//        } finally {
//            ReferenceCountUtil.release(msg); // (2)
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

