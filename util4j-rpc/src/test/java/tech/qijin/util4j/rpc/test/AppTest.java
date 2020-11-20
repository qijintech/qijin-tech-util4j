package tech.qijin.util4j.rpc.test;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    public static void main(String[] args) {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });

        WebClient client = WebClient.builder()
                .baseUrl("https://www.baidu.com")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
        client.method(HttpMethod.GET);
        Mono<String> response = client.get().uri("/").retrieve().bodyToMono(String.class);
        response.blockOptional().ifPresent(System.out::println);
    }
}
