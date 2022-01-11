package tech.qijin.util4j.web.interceptor;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.filter.RequestWrapper;
import tech.qijin.util4j.web.filter.ResponseWrapper;
import tech.qijin.util4j.web.pojo.ResultVo;
import tech.qijin.util4j.web.util.ServletUtil;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger("REQUEST");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof RequestWrapper)) {
            return true;
        }
        RequestWrapper requestWrapper = (RequestWrapper) request;
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);
        LOGGER.info(LogFormat.builder()
                .put("request", request.getQueryString())
                .put("body", requestWrapper.getBodyString())
                .put("uri", request.getRequestURI())
                .put("token", ServletUtil.getHeader(request, "token").orElse(null))
                .build());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long start = (Long) request.getAttribute("start");
        if (start == null) {
            return;
        }
        long end = System.currentTimeMillis();
        LOGGER.info(LogFormat.builder()
                .put("duration", String.valueOf(end - start) + "ms")
                .put("uri", request.getRequestURI())
                .put("httpStatus", String.valueOf(response.getStatus()))
                .build());
    }

    private Optional<ResultVo> parseResult(HttpServletResponse response) throws IOException {
        ResponseWrapper responseWrapper;
        if (response instanceof ResponseWrapper) {
            responseWrapper = (ResponseWrapper) response;
        } else {
            responseWrapper = new ResponseWrapper(response);
        }
        String result = responseWrapper.getContent();
        return Optional.ofNullable(JSON.parseObject(result, ResultVo.class));
    }
}
