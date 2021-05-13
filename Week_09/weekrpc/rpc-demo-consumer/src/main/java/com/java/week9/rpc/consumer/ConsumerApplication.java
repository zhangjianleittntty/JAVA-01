package com.java.week9.rpc.consumer;

import com.alibaba.fastjson.JSON;
import com.java.week9.rpc.api.Order;
import com.java.week9.rpc.api.OrderService;
import com.java.week9.rpccore.client.RpcCoreInvoke;

/**
 * @Program: weekrpc
 * @ClassName: ConsumerApplication
 * @Author: zhangjl
 * @Date: 2021-03-17 21:04
 * @Description:
 */
public class ConsumerApplication {

    public static void main(String[] args) {
        OrderService orderService = RpcCoreInvoke.buildProxy(OrderService.class,"127.0.0.1");
        Order order = orderService.getOrder(1);
        //System.out.println(JSON.toJSON(order));
    }

}
