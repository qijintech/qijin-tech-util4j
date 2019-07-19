package tech.qijin.util4j.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author michealyang
 * @date 2019/3/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class RedisPropertiesConfiguration {
    @Profile("dev")
    @Configuration
    @PropertySource("classpath:redis-dev.properties")
    public static class DevConfiguration {
    }

    @Profile("test")
    @Configuration
    @PropertySource("classpath:redis-test.properties")
    public static class TestConfiguration {
    }

    @Profile("prod")
    @Configuration
    @PropertySource("classpath:redis-prod.properties")
    public static class ProdConfiguration {
    }
}
