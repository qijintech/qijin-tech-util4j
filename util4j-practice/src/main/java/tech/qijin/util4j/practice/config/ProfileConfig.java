package tech.qijin.util4j.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author michealyang
 * @date 2018/11/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class ProfileConfig {
    @Data
    @ConfigurationProperties(prefix = "test")
    public static class Property {
        private String hehe;
    }

    @org.springframework.context.annotation.Profile("dev")
    @Component
    @PropertySource("classpath:config-dev.properties")
    public static class Dev extends Property {
    }

    @org.springframework.context.annotation.Profile("test")
    @Component
    @PropertySource("classpath:config-test.properties")
    public static class Test extends Property {
    }


}
