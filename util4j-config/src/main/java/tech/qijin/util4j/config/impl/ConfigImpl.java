package tech.qijin.util4j.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    @Autowired
    private ConfigProperties configProperties;

    @Override
    public String host() {
        return configProperties.getHost();
    }
}
