package com.java.week9.rpccore.server.nettyserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Program: zjl-project
 * @ClassName: HttpInitializer
 * @Author: zhangjl
 * @Date: 2021-03-13 22:37
 * @Description:
 */
public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        //p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        //p.addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpHandler());
    }
}
