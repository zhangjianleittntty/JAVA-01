package com.java.week9.rpccore.server.nettyserver.geekserver.handler;

import com.java.week9.rpccore.api.geekcommon.Operation;
import com.java.week9.rpccore.api.geekcommon.RequestMessage;
import com.java.week9.rpccore.api.geekcommon.ResponseMessage;
import com.java.week9.rpccore.api.geekcommon.order.OrderOperationResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty 服务端的处理类
 * @author zhangjl
 * @date 2021-05-13
 * @description:
 *     服务端的处理工作:
 *        A. 服务端接受客户端传递过来的数据流:
 *           (1) 粘包半包的解码处理:FrameDecoder
 *           (2) 粘包半包的加码处理:FrameEncoder
 *           (3) 数据流的加码处理:ProtocolEncoder: 服务端向客户端数据:ResponseMessage -> ByteBuf -> out(添加到out队列)
 *           (4) 数据流的解码处理:ProtocolDecoder: 服务端接受客户端数据:ByteBuf -> RequestMessage(因为接受客户端的请求数据) -> out(添加到out队列)
 *        B. 服务端处理工作:
 *           (1) 通过上面所述的解码和加码动作后，Netty已经可以拿到RequestMessage.getMessageBody()
 *           (2) RequestMessage.MessageBody -> OrderOperation
 *           (3) OrderOperation执行execute()
 *           (4) 将得到的结果:OrderOperationResult放到ResponseMessage
 *           (5) 将RequestMessage传递过来的MessageHandler也放到RespsonseMessage
 *           (6) Netty的服务端输出到客户端
 *
 */
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();

        OrderOperationResult orderOperationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHandler(responseMessage.getMessageHandler());
        responseMessage.setMessageBody(orderOperationResult);

        ctx.writeAndFlush(responseMessage);

    }
}
