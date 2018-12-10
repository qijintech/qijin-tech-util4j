package tech.qijin.util4j.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author michealyang
 * @date 2018/12/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Component
@Data
@ConfigurationProperties(prefix = "test")
public class Properties {
    private String hehe;
}
