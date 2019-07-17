package tech.qijin.util4j.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.filter.TraceFilter;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@Order(8)
@Slf4j
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor()).excludePathPatterns(InterceptorExclusion.COMMON);
//        registry.addInterceptor(pageInterceptor2()).excludePathPatterns(InterceptorExclusion.COMMON);
        registry.addInterceptor(channelInterceptor()).excludePathPatterns(InterceptorExclusion.COMMON);
    }

    @ConditionalOnMissingBean(value = {TraceFilter.class})
//    @Bean
    public TraceInterceptor traceInterceptor() {
        log.info(LogFormat.builder().message("TraceInterceptor init").build());
        return new TraceInterceptor();
    }

//    @Bean
    public EnvInterceptor envInterceptor() {
        log.info(LogFormat.builder().message("EnvInterceptor init").build());
        return new EnvInterceptor();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        log.info(LogFormat.builder().message("RequestInterceptor init").build());
        return new RequestInterceptor();
    }

//    @Bean
    public PageInterceptor pageInterceptor() {
        log.info(LogFormat.builder().message("PageInterceptor init").build());
        return new PageInterceptor();
    }

//    @Bean
    public PageInterceptor2 pageInterceptor2() {
        log.info(LogFormat.builder().message("PageInterceptor2 init").build());
        return new PageInterceptor2();
    }

    @Bean
    public ChannelInterceptor channelInterceptor() {
        log.info(LogFormat.builder().message("ChannelInterceptor init").build());
        return new ChannelInterceptor();
    }


}
