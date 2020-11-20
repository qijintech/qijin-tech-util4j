package tech.qijin.util4j.rpc.config.http;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Builder
public class MyWebClientCustomizer implements WebClientCustomizer {
    private static final Logger log = LoggerFactory.getLogger("REMOTE");

    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer retryTimes;

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filter(logRequest());
        webClientBuilder.filter(logResponse());
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS));
                });
        webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)));
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            if (clientRequest.method().equals(HttpMethod.GET)) {
                log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            } else {
                log.info("Request: {} {} {}", clientRequest.method(), clientRequest.url(), clientRequest.body());
            }
            log.info("--- Http Headers of Request: ---");
            clientRequest.headers().forEach(this::logHeader);
            return next.exchange(clientRequest);
        };
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response: {}", clientResponse.statusCode());
            log.info("--- Http Headers of Response: ---");
            clientResponse.headers().asHttpHeaders()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientResponse);
        });
    }

    private void logHeader(String name, List<String> values) {
        values.forEach(value -> log.info("{}={}", name, value));
    }
}
