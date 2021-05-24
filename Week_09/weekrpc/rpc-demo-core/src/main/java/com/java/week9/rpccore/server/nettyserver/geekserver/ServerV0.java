package com.java.week9.rpccore.server.nettyserver.geekserver;

import com.java.week9.rpccore.server.nettyserver.geekserver.codec.OrderFrameDecoder;
import com.java.week9.rpccore.server.nettyserver.geekserver.codec.OrderFrameEncoder;
import com.java.week9.rpccore.server.nettyserver.geekserver.codec.OrderProtocolDecoder;
import com.java.week9.rpccore.server.nettyserver.geekserver.codec.OrderProtocolEncoder;
import com.java.week9.rpccore.server.nettyserver.geekserver.handler.OrderServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerV0 {

    public static void main(String[] args) throws InterruptedException {
        // 声明NettyServer的主线程
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 主线程绑定NIOServerSocketChannel(相当于声明服务端的ServerSocket)
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        // 声明NIOEventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            // NettyServer主线程绑定EventLoopGroup
            serverBootstrap.group(group);
            // 主线程准备处理NioSocketChannel(相当于由ServerSocketChannel声明处SocketChannel)
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    // 开始定义队列:pipeLine
                    ChannelPipeline channelPipeline = ch.pipeline();
                    // 将执行处理放入pipeLine队列
                    channelPipeline.addLast(new OrderFrameDecoder());
                    channelPipeline.addLast(new OrderFrameEncoder());
                    channelPipeline.addLast(new OrderProtocolEncoder());
                    channelPipeline.addLast(new OrderProtocolDecoder());
                    // 队列日志
                    channelPipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    // Server端执行Handler业务处理
                    channelPipeline.addLast(new OrderServerProcessHandler());
                }
            });
            // 主线程绑定端口并执行同步
            ChannelFuture channelFuture = serverBootstrap.bind(8089).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


}
