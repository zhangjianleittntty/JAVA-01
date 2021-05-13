package com.java.week9.rpccore.client.nettyclient.geekclient.codec;


import com.java.week9.rpccore.api.geekcommon.RequestMessage;
import com.java.week9.rpccore.api.geekcommon.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 上下文的转码处理
 * Object(对于服务器端是准备向客户端数据数据对象:ResponseMessage)   ->   ByteBuf
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        requestMessage.encode(byteBuf);
        out.add(requestMessage);
    }
}
