package tech.qijin.util4j.rpc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author michealyang
 * @date 2020-01-10
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Slf4j
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long start = System.currentTimeMillis();
        logRequestDetails(request);
        ClientHttpResponse response = execution.execute(request, body);
        logResponseDetails(response, start);
        return response;
    }

    private void logRequestDetails(HttpRequest request) {
        log.info("Request Headers: {}", request.getHeaders());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getURI());
    }

    private void logResponseDetails(ClientHttpResponse response, long start) throws IOException {
        log.info("Response Code: {}", response.getStatusCode());
        log.info("Response time cost: {} ms", System.currentTimeMillis() - start);
    }
}
