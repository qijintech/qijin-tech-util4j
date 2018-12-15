package tech.qijin.util4j.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tech.qijin.util4j.web.filter.FilterConfiguration;
import tech.qijin.util4j.web.interceptor.InterceptorConfiguration;

/**
 * @author michealyang
 * @date 2018/12/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@Import({FilterConfiguration.class, InterceptorConfiguration.class})
public class WebAutoConfiguration {
}
