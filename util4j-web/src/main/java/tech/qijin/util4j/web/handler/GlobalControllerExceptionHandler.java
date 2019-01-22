package tech.qijin.util4j.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.exception.ValidateException;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.pojo.ResultVo;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo processException(NativeWebRequest request, Exception e) {
        log.error(getUrl(request), e);
        if (e instanceof NullPointerException) {
            return new ResultVo().fail(ResEnum.BAD_GATEWAY).data("内部异常-空指针");
        }

        return new ResultVo().fail(ResEnum.BAD_GATEWAY).data(e.getMessage());
    }

    private String getUrl(NativeWebRequest request) {
        String url = null;
        Object o = request.getNativeRequest();
        if (o instanceof HttpServletRequest) {
            url = ((HttpServletRequest) o).getRequestURI();
        }
        return url;
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public ResultVo processValidateException(ValidateException e) {
        log.warn(LogFormat.builder().message("ValidateException")
                .put("code", e.getCode())
                .put("data", e.getData()).build(), e);
        return new ResultVo().fail(e.getCode(), e.getMessage());
    }


}
