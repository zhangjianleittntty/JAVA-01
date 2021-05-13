package com.java.week9.rpccore.api.geekcommon;


public class RequestMessage extends Message<Operation> {

    @Override
    public Class getOpcodeToOperationClassDecode(int opcode) {
        return OperationType.fromOpcodeToOperationType(opcode).getOperation();
    }

    public RequestMessage() {
    }

    public RequestMessage(long streamId,Operation operation) {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.setVersion(IdUtil.newVersion());
        messageHandler.setStreamId(streamId);
        messageHandler.setOpcode(OperationType.fromOperationClassToOperationType(operation).getOpcode());
        this.setMessageHandler(messageHandler);
        this.setMessageBody(operation);
    }
}
