package com.java.week9.rpccore.client.nettyclient.geekclient.codec;


import com.java.week9.rpccore.api.geekcommon.JsonUtil;
import com.java.week9.rpccore.api.geekcommon.RequestMessage;
import com.java.week9.rpccore.api.geekcommon.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 上下文的转码处理
 * Object(对于服务器端是准备向客户端数据数据对象:ResponseMessage)   ->   ByteBuf
 * 防止内存泄露，内存泄露:ctx.alloc().buffer()声明的ByteBuf没能及时释放
 *    堆外： 内存没有free
 *    对外内存池: 使用没归还
 *    强引用和弱引用:
 *    强引用: String string保镖 = new String(我是主人:强引用)
 *    弱应用: WeakReference<String> string弱保镖 = new WeakReference(new String(我是主人:弱引用))
 *           弱引用遇到GC时，Reference比释放，虽然会释放也会在队列中记录一下。
 *           WeakReference<String> string弱保镖 = new WeakReference(new String(我是主人:弱引用),ReferenceQueue)
 *    检测内存泄露需要在弱引用的RefrerenceQueue中的对象是否已经被GC掉
 *    //**********************************************************************************************************
 *    ByteBuf buffer = ctx.alloc().buffer():  -->  计数器+1,定义弱引用对象DefaultResourceLeak,加到Set(#allLeaks)
 *    buffer.release():                       -->  计数器-1,减到0时，自动执行释放资源，并将弱应用对象在Set中移除
 *
 *    弱引用对象在不在Set里: 在 引用计数没到0 没有执行释放
 *
 *    弱引用指向对象被回收，弱引用放进ReferenceQueue中，可以遍历queue拿出弱引用来判断
 *
 *    方法设置检测级别: -Dio.netty.leakDetection.level = PARANOID
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        requestMessage.encode(byteBuf);
        out.add(byteBuf);
    }
}



















