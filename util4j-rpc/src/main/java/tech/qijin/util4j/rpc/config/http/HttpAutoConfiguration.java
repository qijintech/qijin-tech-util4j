package tech.qijin.util4j.rpc.config.http;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HttpClientBuilder.class)
public class HttpAutoConfiguration {
    static {
        System.out.printf("safd");
    }
}
