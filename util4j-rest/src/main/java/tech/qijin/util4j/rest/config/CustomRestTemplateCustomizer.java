package tech.qijin.util4j.rest.config;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;

/**
 * @author michealyang
 * @date 2020-01-10
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
    }
}
