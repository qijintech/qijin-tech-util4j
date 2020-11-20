package tech.qijin.util4j.rpc.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebFluxTest extends BaseTest {
    @Autowired
    private WebClient demoClient;

    @Test
    public void test() {
        String response = demoClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(System.out::println)
                .doOnSuccess(System.out::println)
                .block();
    }

}
