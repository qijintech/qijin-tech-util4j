package tech.qijin.util4j.utils;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.lang.function.JustRun;

import java.util.function.Supplier;

/**
 * @author michealyang
 * @date 2019-12-19
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Slf4j
public class Util {
    /**
     * 忽略异常的调用
     *
     * @param supplier
     * @param <R>
     * @return
     */
    public static <R> R runIgnoreEx(Supplier<R> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("encountered exception", e);
        }
        return null;
    }

    /**
     * 同 {@link #runIgnoreEx(Supplier)}，支持默认值
     *
     * @param supplier
     * @param defaultValue
     * @param <R>
     * @return
     */
    public static <R> R runIgnoreEx(Supplier<R> supplier, R defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("encountered exception", e);
        }
        return defaultValue;
    }

    public static <R> R runIgnoreEx(Supplier<R> supplier, R defaultValue, String errMsg) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("encountered exception msg={}", errMsg, e);
        }
        return defaultValue;
    }
}
