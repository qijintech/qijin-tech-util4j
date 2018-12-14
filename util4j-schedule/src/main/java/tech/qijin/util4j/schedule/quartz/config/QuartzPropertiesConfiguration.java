package tech.qijin.util4j.schedule.quartz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author michealyang
 * @date 2018/12/12
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class QuartzPropertiesConfiguration {

    @Profile("dev")
    @Configuration
    @PropertySource("classpath:quartz-dev.properties")
    public static class DevConfiguration{
    }

    @Profile("test")
    @Configuration
    @PropertySource("classpath:quartz-test.properties")
    public static class TestConfiguration{
    }

    @Profile("prod")
    @Configuration
    @PropertySource("classpath:quartz-prod.properties")
    public static class ProdConfiguration{
    }
}
