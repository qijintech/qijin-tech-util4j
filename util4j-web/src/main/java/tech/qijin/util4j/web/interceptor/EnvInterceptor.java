package tech.qijin.util4j.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import tech.qijin.util4j.trace.pojo.EnvEnum;
import tech.qijin.util4j.trace.util.EnvUtil;
import tech.qijin.util4j.web.util.ServletUtil;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class EnvInterceptor implements HandlerInterceptor {
    private static final String TEST_KEYWORD = "test";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String host = ServletUtil.getHost(request);
        EnvEnum envEnum = host.contains(TEST_KEYWORD)
                ? EnvEnum.TEST
                : EnvEnum.PRODUCT;
        EnvUtil.setEnv(envEnum);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
