package com.java.week9.rpccore.api.geekcommon;


import com.java.week9.rpccore.api.geekcommon.order.OrderOperation;
import com.java.week9.rpccore.api.geekcommon.order.OrderOperationResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageTestMain {

    public static void main(String[] args) {
        try {
            OrderOperation orderOperation = new OrderOperation();
            orderOperation.setTableId(11);
            orderOperation.setFish("tudoup");
            // 设置request
            RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), orderOperation);
            log.info("requestMessage:{}", JsonUtil.toJson(requestMessage));
            // 执行execute,设置:result
            OrderOperationResult orderOperationResult = orderOperation.execute();
            log.info("orderOperationResult:{}", JsonUtil.toJson(orderOperationResult));
            // responseMessage
            ResponseMessage responseMessage = new ResponseMessage();
            //Class t = responseMessage.getOpcodeToOperationClassDecode(OperationType.fromOperationClassToOperationType(orderOperation).getOpcode());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
