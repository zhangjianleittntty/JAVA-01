package com.java.week9.rpccore.server.nettyserver.geekserver.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 处理转码的粘包和半包
 * @author zhangjl
 * @date 2021-05-13
 */
public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(2);
    }
}
