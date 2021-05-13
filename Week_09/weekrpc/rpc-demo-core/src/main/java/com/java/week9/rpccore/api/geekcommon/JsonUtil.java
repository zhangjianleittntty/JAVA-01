package com.java.week9.rpccore.api.geekcommon;

import com.google.gson.Gson;

public class JsonUtil {
    private static final Gson GSON = new Gson() ;

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T toClass(String strJson,Class<T> classz) {
        return GSON.fromJson(strJson,classz);
    }
}
