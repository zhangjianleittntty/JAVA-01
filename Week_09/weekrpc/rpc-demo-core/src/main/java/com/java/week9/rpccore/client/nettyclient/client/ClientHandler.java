package com.java.week9.rpccore.client.nettyclient.client;

import com.java.week9.rpccore.api.RpcResponse;
import com.java.week9.rpccore.api.netty.RpcNettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Program: weekrpc
 * @ClassName: ClientHandler
 * @Author: zhangjl
 * @Date: 2021-03-19 17:04
 * @Description:
 */
@Slf4j

public class ClientHandler extends SimpleChannelInboundHandler<RpcNettyResponse> {

    @Getter
    private RpcResponse rpcResponse;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcNettyResponse rpcNettyResponse) throws Exception {
        log.error("接收到Server的响应数据:{} ",rpcNettyResponse.toString());
        if (Objects.nonNull(rpcNettyResponse)) {
            rpcResponse.setStatus(true);
            rpcResponse.setResult(rpcNettyResponse);
        } else {
            rpcResponse.setStatus(false);
            rpcResponse.setResult(null);
            rpcResponse.setException(null);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
