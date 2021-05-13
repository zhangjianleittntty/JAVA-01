package com.java.week9.rpccore.api.geekcommon;


import lombok.Data;

@Data
public class MessageHandler {
    private int version;
    private int opcode;
    private long streamId;

}
