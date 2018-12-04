package tech.qijin.util4j.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author michealyang
 * @date 2018/11/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@ConfigurationProperties(prefix = "test")
public class BaseConfig {
    private String hehe;
}
