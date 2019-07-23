package tech.qijin.util4j.web.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.interceptor.EnvInterceptor;
import tech.qijin.util4j.web.interceptor.RequestInterceptor;
import tech.qijin.util4j.web.interceptor.TraceInterceptor;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@Slf4j
public class FilterConfiguration {

    private static final String FILTER_PREFIX = "util4j.web.filter";
    private static final String TRACE_PREFIX = FILTER_PREFIX + ".trace";
    private static final String ENV_PREFIX = FILTER_PREFIX + ".env";

    /**
     * WrapperFilter
     * <p>为了能够无线读取inputstream</p>
     * <p>排第1位</p>
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WrapperFilter> wrapperFilterBean() {
        log.info(LogFormat.builder().message("WrapperFilter init").build());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        WrapperFilter filter = new WrapperFilter();
        registrationBean.setFilter(filter);
        registrationBean.addInitParameter("exclusions", "/favicon.ico");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * TraceFilter
     *
     * @return
     */
    @ConditionalOnProperty(prefix = TRACE_PREFIX, name = "enabled", matchIfMissing = true, havingValue = "true")
    @ConditionalOnMissingBean(value = {TraceInterceptor.class})
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterBean() {
        log.info(LogFormat.builder().message("TraceFilter init").build());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TraceFilter filter = new TraceFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

    /**
     * EnvFilter
     *
     * @return
     */
    @ConditionalOnProperty(prefix = ENV_PREFIX, name = "enabled", matchIfMissing = true, havingValue = "true")
    @ConditionalOnMissingBean(value = {EnvInterceptor.class})
    @Bean
    public FilterRegistrationBean<EnvFilter> envFilterBean() {
        log.info(LogFormat.builder().message("EnvFilter init").build());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        EnvFilter filter = new EnvFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(4);
        return registrationBean;
    }

    /**
     * RequestFilter
     *
     * @return
     */
    @ConditionalOnMissingBean({RequestInterceptor.class})
    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilterBean() {
        log.info(LogFormat.builder().message("RequestFilter init").build());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        RequestFilter filter = new RequestFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(8);
        return registrationBean;
    }

    /**
     * PageFilter
     *
     * @return
     */
//    @Bean
    public FilterRegistrationBean<PageFilter> pageFilterBean() {
        log.info(LogFormat.builder().message("PageFilter init").build());
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        PageFilter filter = new PageFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(16);
        return registrationBean;
    }
}
