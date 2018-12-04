package tech.qijin.util4j.practice.config;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author michealyang
 * @date 2018/11/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Profile("dev")
@Component
@PropertySource("classpath:config-dev.properties")
public class DevConfig extends BaseConfig {
}
