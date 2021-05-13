package com.java.week9.rpccore.server.nettyserver.geekserver.codec;

import com.java.week9.rpccore.api.geekcommon.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 上下文的msg的解码处理
 * ByteBuf(msg) -> object(对于服务端就是接收了客户端传递过来的:requestMessage)
 * @author zhangjl
 * @date 2021-05-13
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msg, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(msg);
        out.add(requestMessage);
    }
}
