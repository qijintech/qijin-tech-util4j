package tech.qijin.util4j.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.dto.ResultDto;
import tech.qijin.util4j.lang.exception.ValidateException;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.utils.ResBuilder;


/**
 * @author michealyang
 * @date 2019/3/25
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@Aspect
@Component
public class DubboProviderAdvice {
//    @Around("CommonPointcut.dubboProviderPointcut()")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(LogFormat.builder().message("start DubboProviderAdvice").build());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class returnType = signature.getReturnType();
        if (!ResultDto.class.equals(returnType)) {
            return joinPoint.proceed();
        }
        try {
            return joinPoint.proceed();
        } catch (ValidateException e) {
            log.warn(LogFormat.builder().message("ValidateException is caught").build());
            return ResBuilder.genError(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.warn(LogFormat.builder().message("Exception is caught").build());
            return ResBuilder.genError(ResEnum.FAIL.code, e.getMessage());
        }

    }
}
