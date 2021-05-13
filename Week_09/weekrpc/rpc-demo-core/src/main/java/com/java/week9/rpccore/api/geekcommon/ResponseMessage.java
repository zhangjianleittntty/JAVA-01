package com.java.week9.rpccore.api.geekcommon;

public class ResponseMessage extends Message<OperationResult> {
    @Override
    public Class getOpcodeToOperationClassDecode(int opcode) {
        return OperationType.fromOpcodeToOperationType(opcode).getOperationResult();
    }
}
