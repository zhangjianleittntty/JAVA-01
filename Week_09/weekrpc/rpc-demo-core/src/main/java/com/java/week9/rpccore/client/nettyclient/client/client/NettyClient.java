package com.java.week9.rpccore.client.nettyclient.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;

/**
 * Netty Client Test: 对应server包下的内容
 * @author zhangjl
 * @date 2021-3
 * Netty客户端连接服务器的事例
 */
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
                            //pipeline.addLast(new HttpClientCodec());
                            pipeline.addLast(new HttpObjectAggregator(1 * 1024));
                            pipeline.addLast(new ClientHandlerChannelInbound()); //客户端处理类

                        }
                    });
            //发起异步连接请求，绑定连接端口和host信息
            final ChannelFuture future = b.connect(host, port).sync();

            this.channel = future.channel();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient("127.0.0.1",8085);
        nettyClient.start();
    }
}
