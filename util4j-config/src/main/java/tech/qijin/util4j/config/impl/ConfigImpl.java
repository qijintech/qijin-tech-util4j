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
    public <T> T get(String key, T defaultValue) {
        Object value = autoConfig.getProperties().get(key);
        return value != null ? (T) value : defaultValue;
    }
}
