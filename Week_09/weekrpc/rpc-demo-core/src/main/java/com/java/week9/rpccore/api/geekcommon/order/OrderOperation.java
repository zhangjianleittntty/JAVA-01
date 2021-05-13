package com.java.week9.rpccore.api.geekcommon.order;

import com.java.week9.rpccore.api.geekcommon.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class OrderOperation extends Operation {
    private int tableId;
    private String fish;

    /**
     * 模拟下单并返回下单结果
     * @return
     */
    public OrderOperationResult execute() {
        log.info("订单请求对象:",toString());
        log.info("订单执行executeing完成");
        OrderOperationResult orderOperationResult = new OrderOperationResult(tableId,fish,true);
        return orderOperationResult;
    }
}
