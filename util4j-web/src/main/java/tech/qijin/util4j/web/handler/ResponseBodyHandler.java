package tech.qijin.util4j.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tech.qijin.util4j.web.annotation.ResponseBodyIgnore;
import tech.qijin.util4j.web.pojo.ResultVo;
import tech.qijin.util4j.web.util.PageHelperProxy;

import java.util.List;

/**
 * 自动封装ResponseBody数据
 * <p>
 * 如，Controller返回结果为list = List<...>，经过此Advice后，会被封装成ResultVo.success().data(list);
 * </p>
 * <p>
 * 如果某些结果不想被封装，请在方法上使用@ResponseBodyIgnore注解
 * </p>
 *
 * @author hanxiao
 */
@Slf4j
@ControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice {

    private static final String DEFAULT_PACKAGE = "tech.qijin";
    @Value("${spring.responseBody.handle.package:}")
    private String handlePackage;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        if (!methodParameter.getMethod().getDeclaringClass().getPackage().getName().startsWith(getHandlePackage())) {
            return false;
        }
        if (null != methodParameter.getMethodAnnotation(ResponseBodyIgnore.class)) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        ResultVo ret = new ResultVo();
        if (o != null) {
            if (o instanceof ResultVo) {
                if (((ResultVo) o).getData() != null && ((ResultVo) o).getData() instanceof List) {
                    ((ResultVo) o).setPage(PageHelperProxy.getResPageVo());
                }
                return o;
            } else {
                ret.success().data(o);
                if (o instanceof List) {
                    ret.setPage(PageHelperProxy.getResPageVo());
                }
            }
        } else {
            // 满足前端关于没内容不行的要求
            ret.success().data("");
        }
        return ret;
    }

    private String getHandlePackage() {
        return StringUtils.isBlank(handlePackage)
                ? DEFAULT_PACKAGE
                : handlePackage;
    }
}
