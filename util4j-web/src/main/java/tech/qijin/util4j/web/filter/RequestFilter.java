package tech.qijin.util4j.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.qijin.util4j.utils.log.LogFormat;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 记录请求参数、耗时等
 *
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RequestFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger("REQUEST");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);
        long end = System.currentTimeMillis();
        LOGGER.info(LogFormat.builder()
                .put("duration", String.valueOf(end - start) + "ms")
                .put("parameters", request.getQueryString())
                .put("uri", request.getRequestURI())
                .put("httpStatus", String.valueOf(response.getStatus()))
                .build());
    }

    @Override
    public void destroy() {

    }
}
