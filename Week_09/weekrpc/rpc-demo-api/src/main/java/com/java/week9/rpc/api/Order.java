package com.java.week9.rpc.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Program: weekrpc
 * @ClassName: Order
 * @Author: zhangjl
 * @Date: 2021-03-17 20:59
 * @Description:
 */
@Data
@Builder
public class Order {
    private Integer id;
    private String name;
    private BigDecimal amount;
}
