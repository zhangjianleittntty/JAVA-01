package com.java.week9.rpccore.client.nettyclient.client;
/*
 * @author uv
 * @date 2018/10/12 20:54
 *
 */

import com.java.week9.rpccore.api.netty.RpcNettyDecoder;
import com.java.week9.rpccore.api.netty.RpcNettyEncoder;
import com.java.week9.rpccore.api.netty.RpcNettyRequset;
import com.java.week9.rpccore.api.netty.RpcNettyResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private final String host;
    private final int port;
    private Channel channel;

    //连接服务端的端口号地址和端口号
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("正在连接中...");
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new RpcNettyEncoder(RpcNettyRequset.class)); //编码request
//                            pipeline.addLast(new RpcNettyDecoder(RpcNettyResponse.class)); //解码response
                            pipeline.addLast(new ClientHandlerChannelInbound()); //客户端处理类

                        }
                    });
            //发起异步连接请求，绑定连接端口和host信息
            final ChannelFuture future = b.connect(host, port).sync();

//            future.addListener(new ChannelFutureListener() {
//
//                @Override
//                public void operationComplete(ChannelFuture arg0) throws Exception {
//                    if (future.isSuccess()) {
//                        System.out.println("连接服务器成功");
//
//                    } else {
//                        System.out.println("连接服务器失败");
//                        future.cause().printStackTrace();
//                        group.shutdownGracefully(); //关闭线程组
//                    }
//                }
//            });
            Thread.sleep(3000);
            this.channel = future.channel();
            //channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public Channel getChannel() {
        return channel;
    }
}
