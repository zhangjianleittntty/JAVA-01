package com.java.week9.rpccore.client.nettyclient.client;

import com.java.week9.rpccore.api.RpcRequest;
import com.java.week9.rpccore.api.RpcResponse;
import com.java.week9.rpccore.api.netty.RpcNettyDecoder;
import com.java.week9.rpccore.api.netty.RpcNettyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Program: weekrpc
 * @ClassName: NettyClient
 * @Author: zhangjl
 * @Date: 2021-03-19 16:15
 * @Description:
 */
@Slf4j
public class NettyClient {

    private final String host;
    private final int port;
    @Getter
    private Channel channel;
    @Getter
    private ClientHandler clientHandler = new ClientHandler();

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        // EventLoopGroup
        final EventLoopGroup group = new NioEventLoopGroup();
        // Bootstrap
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    // 启动连接
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        log.error("正在连接中.............");
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new RpcNettyEncoder(RpcRequest.class));       // 编码request
                        pipeline.addLast(new RpcNettyDecoder(RpcResponse.class));      // 解码response
                        pipeline.addLast(clientHandler);                         // 客户端处理类
                    }
                });
        // 异步请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect(host,port).sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.error("连接服务器成功!");
                } else {
                    log.error("连接服务器失败!");
                    future.cause().printStackTrace();
                    group.shutdownGracefully();     // 关闭线程池组
                }
            }
        });
        // Set Channel
        this.channel = future.channel();
    }


}
