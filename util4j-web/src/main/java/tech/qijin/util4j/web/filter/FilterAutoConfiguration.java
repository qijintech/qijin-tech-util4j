package tech.qijin.util4j.web.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.qijin.util4j.web.interceptor.EnvInterceptor;
import tech.qijin.util4j.web.interceptor.RequestInterceptor;
import tech.qijin.util4j.web.interceptor.TraceInterceptor;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class FilterAutoConfiguration {

    @ConditionalOnMissingBean({TraceInterceptor.class})
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TraceFilter filter = new TraceFilter();
        registrationBean.setFilter(filter);
        registrationBean.addInitParameter("exclusions", "/favicon.ico");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @ConditionalOnMissingBean({EnvInterceptor.class})
    @Bean
    public FilterRegistrationBean<EnvFilter> envFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        EnvFilter filter = new EnvFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(4);
        return registrationBean;
    }

    @ConditionalOnMissingBean({RequestInterceptor.class})
    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        RequestFilter filter = new RequestFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(8);
        return registrationBean;
    }
}
