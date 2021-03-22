package com.java.week9.rpc.api;

import lombok.Builder;
import lombok.Data;

/**
 * @Program: weekrpc
 * @ClassName: User
 * @Author: zhangjl
 * @Date: 2021-03-17 20:58
 * @Description:
 */
@Data
@Builder
public class User {
    private Integer id;
    private String name;

}
