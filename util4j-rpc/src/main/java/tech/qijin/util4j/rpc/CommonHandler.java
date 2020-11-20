package tech.qijin.util4j.rpc;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class CommonHandler {
    public static Consumer<? super Throwable> errorHandler = (res) -> {
        System.out.printf("******************");
        log.error("http request error. err={}", res);
    };
}
