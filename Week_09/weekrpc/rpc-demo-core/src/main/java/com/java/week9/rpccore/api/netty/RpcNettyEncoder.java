package com.java.week9.rpccore.api.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Program: weekrpc
 * @ClassName: RpcNettyEncoder
 * @Author: zhangjl
 * @Date: 2021-03-19 14:44
 * @Description: 编码器: 将实体类转换成可传输的数据
 */
public class RpcNettyEncoder extends MessageToByteEncoder {

    private Class<?> target;

    public RpcNettyEncoder(Class<?> target) {
        this.target = target;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (target.isInstance(msg)) {
            byte[] data = JSON.toJSONBytes(msg);
            out.writeInt(data.length);      // 指定消息头长度
            out.writeBytes(data);           // 消息体包含发送数据
        }
    }
}
