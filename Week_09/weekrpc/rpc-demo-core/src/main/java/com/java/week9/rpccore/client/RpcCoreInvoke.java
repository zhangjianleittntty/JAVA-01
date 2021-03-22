package com.java.week9.rpccore.client;

import com.alibaba.fastjson.JSON;
import com.java.week9.rpccore.api.RpcRequest;
import com.java.week9.rpccore.api.RpcResponse;
import com.java.week9.rpccore.api.netty.RpcNettyRequset;
import com.java.week9.rpccore.client.nettyclient.client.NettyClient;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Program: weekrpc
 * @ClassName: RpcCoreInvoke
 * @Author: zhangjl
 * @Date: 2021-03-18 12:03
 * @Description:
 */
public class RpcCoreInvoke {
    public static <T> T buildProxy(Class<T> classModule,String url) {
        return (T) Proxy.newProxyInstance(classModule.getClass().getClassLoader(),classModule.getClass().getInterfaces(),new RouteClassHandler(classModule,url));
    }

    private static final class RouteClassHandler implements InvocationHandler {
        private Class<?> classModule;
        private String url;

        public RouteClassHandler(Class<?> classModule,String url) {
            this.classModule = classModule;
            this.url = url;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.setServiceClass(method.getClass().getName());
            rpcRequest.setMethodName(method.getName());
            rpcRequest.setParams(args);
            //method.invoke(classModule,args);
            // 请求远程方法，并获取返回值
            RpcResponse rpcResponse = (RpcResponse) post(rpcRequest,url);
            return rpcResponse;
        }

        private Object post(RpcRequest request, String url) throws Exception {
            NettyClient nettyClient = new NettyClient(url,8081);
            // 启动Netty服务
            nettyClient.start();
            // 获取Netty Channel
            Channel channel = nettyClient.getChannel();
            // 消息体
            RpcNettyRequset rpcNettyRequset = new RpcNettyRequset();
            rpcNettyRequset.setId(UUID.randomUUID().toString());
            rpcNettyRequset.setData(JSON.toJSON(request));
            // 通过channel发送消息
            channel.writeAndFlush(rpcNettyRequset);

            return nettyClient.getClientHandler().getRpcResponse();
        }

    }
}
