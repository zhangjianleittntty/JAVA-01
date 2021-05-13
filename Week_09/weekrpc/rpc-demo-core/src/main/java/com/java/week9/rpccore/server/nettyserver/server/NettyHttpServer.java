package com.java.week9.rpccore.server.nettyserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Program: zjl-project
 * @ClassName: NettyHttpServer
 * @Author: zhangjl
 * @Date: 2021-03-13 22:27
 * @Description: Netty Server启动
 */
public class NettyHttpServer {
    public static void main(String[] args) throws InterruptedException {
        int port = 8085;

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap b = new ServerBootstrap();
//            b.option(ChannelOption.TCP_NODELAY,true);
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new HttpInitializer());
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
//                    .childOption(ChannelOption.SO_REUSEADDR, true)
//                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
//                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
//                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
//                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            //.handler(new LoggingHandler(LogLevel.INFO))

            Channel ch = b.bind(port).sync().channel();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
