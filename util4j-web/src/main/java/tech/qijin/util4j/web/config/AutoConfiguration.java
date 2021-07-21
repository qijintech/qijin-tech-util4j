package tech.qijin.util4j.web.config;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
public abstract class AutoConfiguration {
    public abstract String module();

    @PostConstruct
    public void init() {
        log.info("===== {} is loaded =====", module());
    }
}
