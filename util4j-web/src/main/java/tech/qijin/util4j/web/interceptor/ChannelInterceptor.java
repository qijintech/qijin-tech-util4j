package tech.qijin.util4j.web.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.trace.pojo.Channel;
import tech.qijin.util4j.trace.util.ChannelUtil;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.utils.MAssert;
import tech.qijin.util4j.web.annotation.ChannelRequired;
import tech.qijin.util4j.web.util.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
public class ChannelInterceptor implements HandlerInterceptor {

    private static final String CHANNEL_KEYWORD = "channel";
    private static final String GROUP_ID = "tech.qijin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("UserAuthInterceptor  preHandle ...");

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            log.warn("UserAuthInterceptor  not  instanceof HandlerMethod ...");
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        //如果不是self defined的方法，直接通过。如spring自带的/error
        String className = method.getDeclaringClass().getCanonicalName();
        if (!className.startsWith(GROUP_ID)) {
            return true;
        }
        /**
         * 不带{@link ChannelRequired}注解的，也直接通过
         */
        if (method.getAnnotation(ChannelRequired.class) == null) {
            return true;
        }

        Optional<String> channelOpt = ServletUtil.getHeader((HttpServletRequest) request, CHANNEL_KEYWORD);
        if (channelOpt.isPresent()) {
            try {
                Channel channel = Channel.valueOf(channelOpt.get());
                ChannelUtil.setChannel(channel);
            } catch (Exception e) {
                MAssert.isTrue(false, ResEnum.BAD_REQUEST.code, "invalid channel");
            }

            return true;
        } else {
            MAssert.isTrue(false, ResEnum.BAD_REQUEST.code, "channel is required");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
