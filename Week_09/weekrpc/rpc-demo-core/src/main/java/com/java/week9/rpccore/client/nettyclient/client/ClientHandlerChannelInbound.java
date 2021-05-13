package com.java.week9.rpccore.client.nettyclient.client;
/*
 * @author uv
 * @date 2018/10/12 20:56
 * client消息处理类
 */

import com.java.week9.rpccore.api.netty.RpcNettyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.net.URI;

public class ClientHandlerChannelInbound extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    public ClientHandlerChannelInbound() {
        String r = "first request";
        byte[] req = r.getBytes();
        buf = Unpooled.buffer(req.length);
        buf.writeBytes(req);
        System.out.println("send request:" + r);
    }

    /**
     * Read Service Info
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead:" + msg.toString());
        ctx.close();
    }

    /**
     * Action Info
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        // FullHttpRequset
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
        //ctx.writeAndFlush(requestToSQLMAPAPI).sync();
    }

    /**
     * ctx Close
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
