package com.companyname.projectname.util;

import java.util.UUID;

/**
 * 调用链id工具类
 *
 * @author renbaojie
 */
public class TraceIdUtil {

    public static String TRACE_ID = "traceId";

    private static final ThreadLocal<String> THREAD_LOCAL_TRACE_ID = new ThreadLocal<>();

    /**
     * 生成调用链id
     */
    public static String createTraceId() {
        String traceId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        setTraceId(traceId);
        return traceId;
    }

    public static String getTraceId() {
        return THREAD_LOCAL_TRACE_ID.get();
    }

    public static void setTraceId(String traceId) {
        THREAD_LOCAL_TRACE_ID.set(traceId);
    }

    public static void removeTraceId() {
        THREAD_LOCAL_TRACE_ID.remove();
    }

}
