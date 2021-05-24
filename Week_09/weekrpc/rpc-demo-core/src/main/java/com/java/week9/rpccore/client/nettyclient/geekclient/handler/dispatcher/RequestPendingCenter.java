package com.java.week9.rpccore.client.nettyclient.geekclient.handler.dispatcher;

import com.java.week9.rpccore.api.geekcommon.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用Handler类处理响应的返回值,Map<Long,Future>
 * @author zhangjl
 * @date 2021-05-15
 *
 * */
public class RequestPendingCenter {

    private Map<Long,OperationResultFuture> mapResultFuture = new ConcurrentHashMap<>();

    public void add(long streamId,OperationResultFuture operationResultFuture) {
        this.mapResultFuture.put(streamId,operationResultFuture);
    }

    public void set(long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = mapResultFuture.get(streamId);
        operationResultFuture.setSuccess(operationResult);
        this.mapResultFuture.remove(streamId);
    }
}
