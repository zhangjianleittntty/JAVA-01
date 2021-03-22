package com.java.week9.rpccore.api;

import lombok.Data;

/**
 * @Program: weekrpc
 * @ClassName: RpcResponse
 * @Author: zhangjl
 * @Date: 2021-03-18 19:28
 * @Description:
 */
@Data
public class RpcResponse {

    private Object result;
    private boolean status;
    private Exception exception;

}
