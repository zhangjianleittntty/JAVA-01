package com.java.week9.rpccore.api.geekcommon;

import com.java.week9.rpccore.api.geekcommon.order.OrderOperationResult;

public abstract class Operation extends MessageBody {

    public abstract OrderOperationResult execute();

}
