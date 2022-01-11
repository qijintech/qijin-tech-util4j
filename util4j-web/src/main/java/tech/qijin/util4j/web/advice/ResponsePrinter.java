package tech.qijin.util4j.web.advice;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tech.qijin.util4j.utils.LogFormat;

@Slf4j
@ControllerAdvice
public class ResponsePrinter implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (request instanceof ServletServerHttpRequest &&
                response instanceof ServletServerHttpResponse) {
            log.info(LogFormat.builder()
                    .message("response body")
                    .put("body", JSON.toJSONString(body))
                    .build());
        }
        return body;
    }
}
