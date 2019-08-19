package tech.qijin.util4j.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.config.AutoConfig;
import tech.qijin.util4j.config.Config;
import tech.qijin.util4j.config.config.ConfigProperties;

/**
 * @author michealyang
 * @date 2019/3/8
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@Component
public class ConfigImpl implements Config {
    @Value("${module:}")
    private String module;

    private static final String DEBUG_KEY = "debug";
    private static final String MULTI_LOG_KEY = "multiLog";
    private static final String MORE_LOG_KEY = "moreLog";

    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private AutoConfig autoConfig;

    @Override
    public String host() {
        return configProperties.getHost();
    }

    @Override
    public String module() {
        return this.module;
    }

    @Override
    public String configPath() {
        return configProperties.getConfigPath();
    }

    @Override
    public Boolean debugEnabled() {
        String value = (String) autoConfig.getProperties().get(DEBUG_KEY);
        return "true".equals(value);
    }

    @Override
    public Boolean multiLog() {
        String value = (String) autoConfig.getProperties().get(MULTI_LOG_KEY);
        return "true".equals(value);
    }

    @Override
    public Boolean moreLog() {
        String value = (String) autoConfig.getProperties().get(MORE_LOG_KEY);
        return "true".equals(value);
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = (String) autoConfig.getProperties().get(key);
        return value != null ? value : defaultValue;
    }
}
