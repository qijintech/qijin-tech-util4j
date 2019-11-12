package tech.qijin.util4j.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.exception.ValidateException;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.pojo.ResultVo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {BindException.class})
    public ResultVo processValidationException(BindException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResultVo.instance().fail(ResEnum.INVALID_PARAM).message(errors.toString());
    }

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
