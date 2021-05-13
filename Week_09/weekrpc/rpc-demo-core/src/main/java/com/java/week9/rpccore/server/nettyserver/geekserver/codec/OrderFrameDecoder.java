package com.java.week9.rpccore.server.nettyserver.geekserver.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * OrderFrameDecoder: 处理解码的粘包和半包情况
 * @author zhangjl
 * @date 2021-05-13
 * @description:
 *
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
