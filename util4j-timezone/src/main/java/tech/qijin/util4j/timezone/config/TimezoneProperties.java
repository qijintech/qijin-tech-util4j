package tech.qijin.util4j.timezone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author michealyang
 * @date 2019/2/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@ConfigurationProperties(prefix = "timezone")
public class TimezoneProperties {
    private String value;
}
