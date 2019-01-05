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
public class FilterConfiguration {

    /**
     * WrapperFilter
     * <p>为了能够无线读取inputstream</p>
     * <p>排第1位</p>
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WrapperFilter> wrapperFilterBean() {
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
    @ConditionalOnMissingBean({TraceInterceptor.class})
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterBean() {
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
    @ConditionalOnMissingBean({EnvInterceptor.class})
    @Bean
    public FilterRegistrationBean<EnvFilter> envFilterBean() {
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
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        PageFilter filter = new PageFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(16);
        return registrationBean;
    }
}
