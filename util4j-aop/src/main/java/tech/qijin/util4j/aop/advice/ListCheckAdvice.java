package tech.qijin.util4j.aop.advice;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.qijin.util4j.aop.annotation.FailurePolicy;
import tech.qijin.util4j.aop.annotation.Retry;
import tech.qijin.util4j.lang.exception.ValidateException;

import java.util.*;

/**
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer: 一个例子：https://blog.csdn.net/dm_vincent/article/details/72851240
 * 另一个例子: https://www.baeldung.com/spring-aop-annotation
 **/
@Aspect
@Component
public class ListCheckAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ListCheckAdvice.class);

    @Around("CommonPointcut.listCheckAnnotationPointcut()")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class returnClass = signature.getReturnType();
        Object[] params = joinPoint.getArgs();
        if (params != null
                && params.length == 1
                && (params[0] instanceof List || params[0] instanceof Set)
                && CollectionUtils.isEmpty((Collection) params[0])) {
            if (List.class.equals(returnClass)) {
                return Collections.emptyList();
            } else if (Set.class.equals(returnClass)) {
                return Collections.emptySet();
            } else if (Map.class.equals(returnClass)) {
                return Collections.emptyMap();
            }
        }
        return joinPoint.proceed();
    }
}
