package com.java.week9.rpccore.api.geekcommon;

import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * Netty传递Message消息
 *      Message[MessageHandler,MessageBody]: 包括:消息头和消息体
 *      MessageHandler:消息头:version,opcode,streamId
 *      MessageBody:消息体:OrderOperation,AuthOperation
 * 方法:decode:ByteBuf -> Object
 *     encode:Object -> ByteBuf
 * @param <T>
 * @author zhangjl
 * @date 2021-05-11
 * @Description:
 * Message<T extends MessageBody>: 用于泛型设置，T在类中的声明使用，<>泛型中使用了什么类型，类中的T就是使用的哪种类型
 */
@Data
public abstract class Message <T extends MessageBody> {

    private MessageHandler messageHandler;
    private T messageBody;

    /**
     * 加码: Object(Message[MessageHandler,MessageBody]) -> ByteBuf
     * @param byteBuf
     * @Description: Netty准备发送消息，需要将Object准换成传输可以识别的二进制(ByteBuf)
     */
    public void encode(ByteBuf byteBuf) {
        try {
            byteBuf.writeInt(messageHandler.getVersion());
            byteBuf.writeLong(messageHandler.getStreamId());
            byteBuf.writeInt(messageHandler.getOpcode());

            byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Class<T> getOpcodeToOperationClassDecode(int opcode);

    /**
     * 解码: ByteBuf(Msg) -> Object(Message[MessageHandler,MessageBody])
     * @param msg
     * @Description: Netty接收到消息时，消息是二进制格式(ByteBuf),需要将二进制格式(ByteBuf)准换成应用可以识别的:Object
     */
    public void decode(ByteBuf msg) {
        try {
            // 消息头-MessageHandler
            // 应该使用临时变量，流数据取一次即可
            int version = msg.readInt();
            long streamId = msg.readLong();
            int opcode = msg.readInt();
            // 设置MessageHandler
            MessageHandler messageHandler = new MessageHandler();
            messageHandler.setVersion(version);
            messageHandler.setStreamId(streamId);
            messageHandler.setOpcode(opcode);
            this.messageHandler = messageHandler;

            // 消息体-MessageBody
            // 设置MessageBody: 将msg解码成Object
            // 获取请求对象的Class
            Class<T> bodyClazz = getOpcodeToOperationClassDecode(opcode);
            this.messageBody = JsonUtil.toClass(msg.toString(Charset.forName("UTF-8")), bodyClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
