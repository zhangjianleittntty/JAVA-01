package com.java.week9.rpccore.client.nettyclient.client.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty ClientHandlerChannelInbound
 * @author zhangjl
 * @date 2021-03
 * Netty事例，Client发送请求到Server,并获取Server返回的消息
 */
public class ClientHandlerChannelInbound extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    public ClientHandlerChannelInbound() {
        String r = "Netty Client发送请求消息";
        byte[] req = r.getBytes();
        buf = Unpooled.buffer(req.length);
        buf.writeBytes(req);
        System.out.println("send request:" + r);
    }

    /**
     * 读取Server响应的Msg
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 解析Response
        ByteBuf refResponse = (ByteBuf) msg;
        byte[] byteResponseData = new byte[refResponse.readableBytes()];
        refResponse.readBytes(byteResponseData);

        String  strResponse = new String(byteResponseData,"UTF-8");
        System.out.println("Netty Client 读取 Netty Response返回的消息:" + strResponse);

        ctx.close();
    }

    /**
     * 发送Msg到Netty Server
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
//        URI uri = new URI("/");
//        FullHttpRequest requestToSQLMAPAPI = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
//                //uri.toASCIIString(), Unpooled.wrappedBuffer(ctx.getBytes("UTF-8")));
//        uri.toASCIIString(), null);
//
//        requestToSQLMAPAPI.headers().set(HttpHeaders.Names.HOST, "127.0.0.1");
//        requestToSQLMAPAPI.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//        requestToSQLMAPAPI.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
//                requestToSQLMAPAPI.content().readableBytes());
//        requestToSQLMAPAPI.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        ctx.writeAndFlush(buf);
    }

    /**
     * 上下文关闭
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
