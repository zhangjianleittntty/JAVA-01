package com.java.week9.rpccore.client.nettyclient.client;
/*
 * @author uv
 * @date 2018/10/12 20:56
 * client消息处理类
 */

import com.java.week9.rpccore.api.netty.RpcNettyResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;

public class ClientHandler extends SimpleChannelInboundHandler<RpcNettyResponse> {

    //处理服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcNettyResponse response) throws Exception {
        System.out.println("接受到server响应数据: " + response.toString());
    }

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
        ctx.writeAndFlush(null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
