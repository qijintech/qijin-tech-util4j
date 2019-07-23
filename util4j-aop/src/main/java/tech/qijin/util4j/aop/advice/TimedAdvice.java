package tech.qijin.util4j.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tech.qijin.util4j.utils.LogFormat;

/**
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer: 一个例子：https://blog.csdn.net/dm_vincent/article/details/72851240
 * 另一个例子: https://www.baeldung.com/spring-aop-annotation
 **/
@Aspect
@Component
public class TimedAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimedAdvice.class);

    @Around("CommonPointcut.timedAnnotationPointcut()")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = signature.getMethod().getDeclaringClass().getSimpleName();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long cost = System.currentTimeMillis() - start;
        String message = (new StringBuilder())
                .append("TIMED [")
                .append(className)
                .append(".")
                .append(methodName)
                .append("()")
                .append("]")
                .toString();
        LOGGER.info(LogFormat.builder()
                .message(message)
                .put("cost", String.valueOf(cost) + "ms")
                .build());
        return result;
    }
}
