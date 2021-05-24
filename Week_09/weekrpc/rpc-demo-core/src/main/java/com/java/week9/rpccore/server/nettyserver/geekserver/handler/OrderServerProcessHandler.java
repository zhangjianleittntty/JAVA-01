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
 *      模拟内存泄露:
 *        1. 定义一个ByteBuf，不释放
 *        2. 客户端大量大发送请求消息
 *      标记注释:
 *        1. @ChannelHandler.Sharable: pipeline中的Handler是否可以重复加入，多个client同时访问时
 *        2. @Skip 是否跳过执行方法
 *        2. @UnstableApi: 标识不建议使用
 *      业务场景优化:
 *        CPU密集型: 运算
 *        IO密集型: 等待
 *            new Unordered
 *      增强写数据的性能
 *
 *      优化应用限流:
 *      全局限流和Channel级别限流
 *      GlobalTrafficShapingHandler和ChannelTrafficShapinghandler: 每个都有2个参数，限制进和限制处
 *      Global和Channel可以合并处理限流,GlobalChannelTrafficShapingHandler: 则包含了4个参数
 *
 *      优化使用native:为不同的平台开启native
 *         加载开关、
 *         环境加载路径、
 *         创建临时文件，拷贝native包文件，加载执行后删除
 *         问题: 版本不一致或加载执行权限受限 -> mount -o remount,exec /tmp
 *
 */
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();

        OrderOperationResult orderOperationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHandler(requestMessage.getMessageHandler());
        responseMessage.setMessageBody(orderOperationResult);

        ctx.writeAndFlush(responseMessage);

    }
}
