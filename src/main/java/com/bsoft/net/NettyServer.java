package com.bsoft.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by sky on 2018/6/20.
 */
public class NettyServer {

    int port = 9392;

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGruop = new NioEventLoopGroup();
        try{

            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workGruop)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
//                                    .addLast(new NioServer())
                                    .addLast("decoder", new HttpRequestDecoder())   // 1
                                    .addLast("encoder", new HttpResponseEncoder())  // 2
                                    .addLast("aggregator", new HttpObjectAggregator(512 * 1024))    // 3
                                    .addLast("handler", new HttpHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            workGruop.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void start(){
        try {
            new NettyServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        try {
            Registry.registryUrl("/baidu", "http://www.baidu.com");
            Registry.registryUrl("/hcn2", "http://10.1.3.153:8081/outpatient/doctor");

        } catch (Exception e) {
            e.printStackTrace();
        }
        NettyServer.start();
    }
}
