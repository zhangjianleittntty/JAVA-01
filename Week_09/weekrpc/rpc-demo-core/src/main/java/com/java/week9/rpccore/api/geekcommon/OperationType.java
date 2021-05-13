package com.java.week9.rpccore.api.geekcommon;

import com.java.week9.rpccore.api.geekcommon.order.OrderOperation;
import com.java.week9.rpccore.api.geekcommon.order.OrderOperationResult;

import java.util.function.Predicate;

public enum OperationType {

    ORDEROPERATION(3, OrderOperation.class, OrderOperationResult.class);

    private int opcode;
    private Class<? extends Operation> operation;
    private Class<? extends OperationResult> operationResult;

    OperationType(int opcode, Class<? extends Operation> operation, Class<? extends OperationResult> operationResult) {
        this.opcode = opcode;
        this.operation = operation;
        this.operationResult = operationResult;
    }

    public int getOpcode() {
        return opcode;
    }

    public Class<? extends Operation> getOperation() {
        return operation;
    }

    public Class<? extends OperationResult> getOperationResult() {
        return operationResult;
    }

    /**
     * 通过opcode获取枚举对应的OperationType
     * @param opcode
     * @return
     */
    public static OperationType fromOpcodeToOperationType(int opcode) {
        return getOperationType(requestType -> requestType.opcode == opcode);
    }

    /**
     * 通过Operation.Class获取枚举对应的OperationType
     * @param operation
     * @return
     */
    public static OperationType fromOperationClassToOperationType(Operation operation) {
        return getOperationType(requestType -> requestType.operation == operation.getClass());
    }

    /**
     * Predicate: 断言校验，将校验行为传递给方法进行校验
     * @param operationTypePredicate
     * @return
     * @description:
     *     Predicate<对象>校验行为
     *     operationTypePredicate.test(operationType)
     *     ==>  校验行为去校验operationType对象
     */
    public static OperationType getOperationType(Predicate<OperationType> operationTypePredicate) {
        for (OperationType operationType : values()) {
            if (operationTypePredicate.test(operationType)) {
                return operationType;
            }
        }
        throw new AssertionError("no found operationType!");
    }
}
