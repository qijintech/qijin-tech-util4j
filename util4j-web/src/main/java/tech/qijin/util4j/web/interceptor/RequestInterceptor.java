package tech.qijin.util4j.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.qijin.util4j.web.util.LogFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger("REQUEST");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("request interceptor start");
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long start = (Long) request.getAttribute("start");
        long end = System.currentTimeMillis();
        System.out.println("request interceptor end");
        LOGGER.info(LogFormat.builder()
                .put("duration", String.valueOf(end - start) + "ms")
                .put("request", "{" + request.getQueryString() + "}")
                .put("uri", request.getRequestURI())
                .put("httpStatus", String.valueOf(response.getStatus()))
                .build());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
