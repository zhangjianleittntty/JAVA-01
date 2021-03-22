package com.java.week9.rpccore.api.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Program: weekrpc
 * @ClassName: RpcNettyDecoder
 * @Author: zhangjl
 * @Date: 2021-03-19 14:44
 * @Description: 解码器：将接收到的数据转换成实体类
 */
public class RpcNettyDecoder extends ByteToMessageDecoder {

    private Class<?> target;      // 目标对象解码

    public RpcNettyDecoder(Class<?> target) {
        this.target = target;
    }

    /**
     * in 解码 -> 读入到 data
     * data 通过 JSON 转换成需要的对象
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // in流长度是否允许
        if (in.readableBytes() < 4) {
            return;
        }
        // 标记当前的readIndex位置
        in.markReaderIndex();
        // 读取传过来的长度
        int dataLength = in.readInt();
        // 读到消息体长度小于传过来长度,则resetReaderIndex(重置访问in流的Index),配合markReaderIndex,把readIndex重置到mark的地方
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        // in 解码后 -> 读入到 Data
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        // data 通过 JSON ->  需要的Object
        Object object = JSON.parseObject(data,target);
        // object 写入到:out
        out.add(object);
    }
}
