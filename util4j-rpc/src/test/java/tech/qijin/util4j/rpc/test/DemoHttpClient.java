package tech.qijin.util4j.rpc.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import tech.qijin.util4j.rpc.config.http.HttpClientBuilder;

@Configuration
public class DemoHttpClient {
    @Bean("demoBuilder")
    public HttpClientBuilder wxApiBuilder() {
        return HttpClientBuilder.create();
    }

    @Bean("demoClient")
    public WebClient wxApi(@Qualifier("demoBuilder") HttpClientBuilder wxApiBuilder) {
        return wxApiBuilder.build("https://www.baidu.com");
    }
}
