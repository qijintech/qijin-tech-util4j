package tech.qijin.util4j.web.advice;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import tech.qijin.util4j.utils.LogFormat;

import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
public class RequestPrinter extends RequestBodyAdviceAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        // 打印body内容
        log.info(LogFormat.builder()
                .message("request body")
                .put("body", JSON.toJSONString(body))
                .build());
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
