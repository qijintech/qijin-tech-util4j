package tech.qijin.util4j.trace.util;

import java.util.UUID;

/**
 * @author michealyang
 * @date 2018/11/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class TraceUtil {
    private static ThreadLocal<String> traceThreadLocal = new ThreadLocal();

    public static void setTraceId(String traceId) {
        traceThreadLocal.set(traceId);
    }

    public static void setTraceId() {
        traceThreadLocal.set(genTraceId());
    }

    public static String getTraceId() {
        return traceThreadLocal.get();
    }

    public static void remove() {
        traceThreadLocal.remove();
    }

    public static String genTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
