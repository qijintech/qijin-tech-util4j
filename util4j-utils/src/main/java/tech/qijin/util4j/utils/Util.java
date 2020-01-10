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
     * 执行时忽略异常，只打印log，防止影响主流程
     *
     * @param supplier
     * @param errMessage 出现异常时的错误日志信息
     * @param <R>
     * @return
     */
    public static <R> R runIgnoreEx(Supplier<R> supplier, String errMessage) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("encountered exception. msg={}", errMessage, e);
        }
        return null;
    }

    /**
     * 同{@link #runIgnoreEx(Supplier, String)}，只是无需传入errMessage
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

    public static void runIgnoreEx(JustRun justRun, String errMessage) {
        try {
            justRun.run();
        } catch (Exception e) {
            log.error("encountered exception. msg={}", errMessage, e);
        }
    }

    public static void runIgnoreEx(JustRun justRun) {
        try {
            justRun.run();
        } catch (Exception e) {
            log.error("encountered exception", e);
        }
    }
}
