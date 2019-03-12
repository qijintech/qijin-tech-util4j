package tech.qijin.util4j.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class WrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 暂时不要用ResponseWrapper。
     * 当需要返回页面view时，ResponseWrapper会导致不显示结果
     * <p>
     * 如果需要打印response code，可以通过{@link org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice}来实现
     * </p>
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
//        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
