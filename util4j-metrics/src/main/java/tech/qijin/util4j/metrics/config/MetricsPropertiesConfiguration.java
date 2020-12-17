package tech.qijin.util4j.metrics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author michealyang
 * @date 2019/3/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class MetricsPropertiesConfiguration {
    @Profile("dev")
    @Configuration
    @PropertySource("classpath:metrics-dev.properties")
    public static class DevConfiguration {
    }

    @Profile("test")
    @Configuration
    @PropertySource("classpath:metrics-test.properties")
    public static class TestConfiguration {
    }

    @Profile("prod")
    @Configuration
    @PropertySource("classpath:metrics-prod.properties")
    public static class ProdConfiguration {
    }

    @Profile("stress")
    @Configuration
    @PropertySource("classpath:metrics-stress.properties")
    public static class StressConfiguration {
    }

    @Profile("staging")
    @Configuration
    @PropertySource("classpath:metrics-staging.properties")
    public static class StagingConfiguration {
    }
}
