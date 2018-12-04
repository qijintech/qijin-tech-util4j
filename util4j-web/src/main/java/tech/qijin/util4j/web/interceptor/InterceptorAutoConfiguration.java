package tech.qijin.util4j.web.interceptor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.qijin.util4j.web.filter.EnvFilter;
import tech.qijin.util4j.web.filter.RequestFilter;
import tech.qijin.util4j.web.filter.TraceFilter;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
//@Configuration
@ConditionalOnMissingBean({RequestFilter.class, TraceFilter.class, EnvFilter.class})
public class InterceptorAutoConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).excludePathPatterns(InterceptorExclusion.COMMON);
        registry.addInterceptor(new EnvInterceptor()).excludePathPatterns(InterceptorExclusion.COMMON);
        registry.addInterceptor(new RequestInterceptor()).excludePathPatterns(InterceptorExclusion.COMMON);
    }
}
