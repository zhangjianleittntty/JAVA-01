package com.java.week9.rpccore.client.nettyclient.geekclient.handler.dispatcher;

import com.java.week9.rpccore.api.geekcommon.OperationResult;
import io.netty.util.concurrent.DefaultPromise;

/**
 * Netty获取返回值操作
 * @author zhangjl
 * @date:2021-05-15
 * @description:
 *     Netty获取返回值，要做Future异步化，在有响应的返回值时就做处理，请求不阻塞，提升请求访问效率
 *     重点三步操作:
 *        1. 请求Map放入标识和声明的Future:Map.put()
 *        2. 响应时查询是否有对应的标识，有了标识就获取对应的Future: Map.get()
 *        3. 响应的数据放入到Future中:Future.set()
 *        4. 在Future.get()中获取值:Future.get()
 */
public class OperationResultFuture extends DefaultPromise<OperationResult> {
}
