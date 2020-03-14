package tech.qijin.util4j.rest.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @author michealyang
 * @date 2020-01-10
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
public class RestTemplateTest extends BaseTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate() {
        String url = "https://www.baidu.com";
        String res = restTemplate.getForObject(url, String.class);
        log.info("res={}", res);
    }
}
