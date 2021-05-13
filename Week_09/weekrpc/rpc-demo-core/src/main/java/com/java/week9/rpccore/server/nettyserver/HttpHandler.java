package com.java.week9.rpccore.server.nettyserver;

import com.alibaba.fastjson.JSON;
import com.java.week9.rpc.api.Order;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;

import java.math.BigDecimal;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Program: zjl-project
 * @ClassName: HttpHandler
 * @Author: zhangjl
 * @Date: 2021-03-13 22:38
 * @Description:
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelReadA:");
        try {
            //FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
//            String uri = fullHttpRequest.uri();
////            if (uri.contains("/test")) {
////                handlerTest(fullHttpRequest,ctx);
////            }
//            System.out.println("channelReadB:" + JSON.toJSONString(fullHttpRequest));
//            handlerTest(fullHttpRequest,ctx);
            ByteBuf byteBufRequest = (ByteBuf)msg;
            byte[] byteRequestData = new byte[byteBufRequest.readableBytes()];
            byteBufRequest.readBytes(byteRequestData);    // ByteBuf -> byte
            String strRequest = new String(byteRequestData,"UTF-8");       // byte -> String
            System.out.println("channelReadB:" + JSON.toJSONString(strRequest));

            // Response响应返回
//            Order orderResponse = Order.builder().id(1122).name("第一单").amount(new BigDecimal("345.2")).build();
//            String strResponse = JSON.toJSONString(orderResponse);
            byte[] byteResponseDate = "cccc".getBytes();
            ByteBuf byteBufResponse = Unpooled.copiedBuffer(byteResponseDate);
            ctx.writeAndFlush(byteBufResponse);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handlerTest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        FullHttpResponse response = null;
        try {
            String value = "hello,This is result of gateway! \n";
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
            System.out.println(value);
        } catch (Exception e) {
            System.out.println("处理出错:"+e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
