package com.java.week9.rpccore.api.geekcommon.order;

import com.java.week9.rpccore.api.geekcommon.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderOperationResult extends OperationResult {
    private int tableId;
    private String fish;
    private boolean complate;
}
