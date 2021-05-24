package com.java.week9.rpccore.client.nettyclient.geekclient.codec;

import com.java.week9.rpccore.api.geekcommon.IdUtil;
import com.java.week9.rpccore.api.geekcommon.Operation;
import com.java.week9.rpccore.api.geekcommon.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OperationRequestMessageEncoder extends MessageToMessageEncoder<Operation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Operation operation, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(),operation);
        ctx.writeAndFlush(requestMessage);
    }
}
