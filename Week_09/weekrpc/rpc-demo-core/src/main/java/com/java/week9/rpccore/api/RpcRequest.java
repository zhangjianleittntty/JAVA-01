package com.java.week9.rpccore.api;

import lombok.Data;

/**
 * @Program: weekrpc
 * @ClassName: RpcRequest
 * @Author: zhangjl
 * @Date: 2021-03-18 19:26
 * @Description:
 */
@Data
public class RpcRequest {

    private String serviceClass;
    private String methodName;
    private Object[] params;

}
