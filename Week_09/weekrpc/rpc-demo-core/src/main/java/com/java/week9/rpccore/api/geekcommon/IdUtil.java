package com.java.week9.rpccore.api.geekcommon;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class IdUtil {
    private static final AtomicLong IDX = new AtomicLong();
    private static final AtomicInteger IDVERSION = new AtomicInteger();

    public static long nextId() {
        return IDX.incrementAndGet();
    }

    public static int newVersion() {
        return IDVERSION.incrementAndGet();
    }
}
