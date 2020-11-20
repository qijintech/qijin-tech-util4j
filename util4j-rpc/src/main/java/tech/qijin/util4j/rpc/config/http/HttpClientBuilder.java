package tech.qijin.util4j.rpc.config.http;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@ConfigurationProperties(prefix = "spring.util4j.rpc.http")
public class HttpClientBuilder {
    private final Integer DEFAULT_CONNECT_TIMEOUT = 5000;
    private final Integer DEFAULT_READ_TIMEOUT = 10000;
    private final Integer DEFAULT_WRITE_TIMEOUT = 10000;
    private final Integer DEFAULT_RETRY_TIMES = 0;

    private String host;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer retryTimes;

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    public WebClient build() {
        String host = this.host;
        if (StringUtils.isNotBlank(host)) {
            throw new IllegalArgumentException("host is needed");
        }
        if (!host.startsWith("http")) {
            host = "http://" + host;
        }
        return webClientBuilder().baseUrl(host).build();
    }

    public WebClient build(String host) {
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("host is needed");
        }
        if (!host.startsWith("http")) {
            host = "http://" + host;
        }
        return webClientBuilder().baseUrl(host).build();
    }

    WebClientCustomizer webClientCustomizer() {
        return MyWebClientCustomizer.builder()
                .connectTimeout(ifAbsent(this.connectTimeout, DEFAULT_CONNECT_TIMEOUT))
                .readTimeout(ifAbsent(this.readTimeout, DEFAULT_READ_TIMEOUT))
                .writeTimeout(ifAbsent(this.writeTimeout, DEFAULT_WRITE_TIMEOUT))
                .retryTimes(ifAbsent(this.readTimeout, DEFAULT_RETRY_TIMES))
                .build();
    }

    WebClient.Builder webClientBuilder() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        webClientCustomizer().customize(webClientBuilder);
        return webClientBuilder;
    }

    private <T> T ifAbsent(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
