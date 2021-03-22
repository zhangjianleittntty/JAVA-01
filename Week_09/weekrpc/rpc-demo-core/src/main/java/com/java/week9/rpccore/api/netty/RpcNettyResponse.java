package com.java.week9.rpccore.api.netty;

import lombok.Data;

/**
 * @Program: weekrpc
 * @ClassName: RpcNettyRequset
 * @Author: zhangjl
 * @Date: 2021-03-18 21:02
 * @Description:
 */
@Data
public class RpcNettyResponse {
    private String id;
    private Object data;
    private Integer status;      //1:正常;0:失败

}
