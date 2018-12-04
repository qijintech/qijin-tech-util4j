package tech.qijin.util4j.web.util;

import tech.qijin.util4j.web.pojo.EnvEnum;

/**
 * @author michealyang
 * @date 2018/11/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class EnvUtil {
    private static ThreadLocal<EnvEnum> envThreadLocal = new ThreadLocal();

    public static void setEnv(EnvEnum envEnum) {
        envThreadLocal.set(envEnum);
    }

    public static EnvEnum getEnv() {
        return envThreadLocal.get();
    }

    public static void remove() {
        envThreadLocal.remove();
    }
}
