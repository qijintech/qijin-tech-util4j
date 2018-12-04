package tech.qijin.util4j.web.filter;

import tech.qijin.util4j.web.util.TraceUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class TraceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        TraceUtil.setTraceId();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
