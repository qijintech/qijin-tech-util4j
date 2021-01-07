package tech.qijin.util4j.config.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author michealyang
 * @date 2019/3/8
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@ComponentScan(value = "tech.qijin.util4j.config")
@EnableConfigurationProperties(ConfigProperties.class)
public class ConfigAutoConfiguration {
}
