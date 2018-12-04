package tech.qijin.util4j.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.exception.ValidateException;
import tech.qijin.util4j.web.pojo.ResultVo;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo processException(NativeWebRequest request, Exception e) {
        LOGGER.error(getUrl(request), e);
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
        LOGGER.warn("ValidateException :",e.getMessage());
        return new ResultVo().fail(e.getCode(), e.getMessage());
    }




}
