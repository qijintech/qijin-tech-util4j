package tech.qijin.util4j.timezone.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author michealyang
 * @date 2019/2/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@ComponentScan("tech.qijin.util4j.timezone")
@EnableConfigurationProperties({TimezoneProperties.class})
@Import(TimezonePropertiesConfiguration.class)
public class TimezoneAutoConfiguration {
}
