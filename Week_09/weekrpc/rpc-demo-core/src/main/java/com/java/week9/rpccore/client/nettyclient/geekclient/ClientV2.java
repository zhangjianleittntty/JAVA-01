package com.java.week9.rpccore.client.nettyclient.geekclient;

import com.java.week9.rpccore.api.geekcommon.*;
import com.java.week9.rpccore.api.geekcommon.order.OrderOperation;
import com.java.week9.rpccore.client.nettyclient.geekclient.codec.*;
import com.java.week9.rpccore.client.nettyclient.geekclient.handler.dispatcher.OperationResultFuture;
import com.java.week9.rpccore.client.nettyclient.geekclient.handler.dispatcher.RequestPendingCenter;
import com.java.week9.rpccore.client.nettyclient.geekclient.handler.dispatcher.ResponseDispatcherHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class ClientV2 {

    public static void main(String[] args) throws InterruptedException,ExecutionException{
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);

        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group);
            // 定义中心处理的:Future
            RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new OrderFrameDecoder());
                    pipeline.addLast(new OrderFrameEncoder());

                    pipeline.addLast(new OrderProtocolEncoder());
                    pipeline.addLast(new OrderProtocolDecoder());

                    //响应信息的Future处理
                    pipeline.addLast(new ResponseDispatcherHandler(requestPendingCenter));

                    // 请求加码处理类
                    pipeline.addLast(new OperationRequestMessageEncoder());
                    // 队列日志
                    pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                }
            });
            //(1). 绑定端口
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8089);
            channelFuture.sync();
            //(2). 设置请求RequestMessage
            long streamId = IdUtil.nextId();
            OrderOperation orderOperation = new OrderOperation(1001,"tudou");
            RequestMessage requestMessage = new RequestMessage(streamId,orderOperation);

            //(3). 获取Server -> Client的ResponseMessage的响应值,绑定对响应结果的获取:Future
            OperationResultFuture operationResultFuture = new OperationResultFuture();
            requestPendingCenter.add(streamId,operationResultFuture);

            //(4). 发起向Server的请求
            channelFuture.channel().writeAndFlush(requestMessage);

            // 输出Server -> Client的响应值: 阻塞请求段的响应
            OperationResult operation = operationResultFuture.get();
            System.out.println("OperationResult:" + JsonUtil.toJson(operation));
            //(5). 关闭Sync()
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
