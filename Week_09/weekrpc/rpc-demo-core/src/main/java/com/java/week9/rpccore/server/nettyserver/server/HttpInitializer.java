package com.java.week9.rpccore.server.nettyserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Program: zjl-project
 * @ClassName: HttpInitializer
 * @Author: zhangjl
 * @Date: 2021-03-13 22:37
 * @Description: SocketChannel 赋予Handler处理能力
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        //p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        //p.addLast(new HttpObjectAggregator(1 * 1024));
        p.addLast(new HttpHandler());
    }
}
