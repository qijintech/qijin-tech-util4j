package tech.qijin.util4j.aop.advice;

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

/**
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer: 一个例子：https://blog.csdn.net/dm_vincent/article/details/72851240
 * 另一个例子: https://www.baeldung.com/spring-aop-annotation
 **/
@Aspect
@Component
public class RetryAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RetryAdvice.class);

    @Around("CommonPointcut.retryAnnotationPointcut()")
    public Object retry(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Retry retry = joinPoint.getTarget()
                .getClass()
                .getMethod(methodName, parameterTypes)
                .getAnnotation(Retry.class);
        int times = retry.times();
        Exception exception = null;
        while (times-- > 0) {
            try {
                logger.info("start retry advance");
                Object result = joinPoint.proceed();
                return result;
            } catch (ValidateException | NullPointerException e) {
                throw e;
            } catch (Exception e) {
                exception = e;
                logger.error("encounter exception, will retry {} times", times, e);
                continue;
            }
        }
        FailurePolicy policy = retry.failurePolicy();
        switch (policy) {
            case EXCPTION:
                //抛出原始异常
                throw exception;
            case MQ:
                //TODO send to mq
                break;
            default:
                throw exception;
        }
        return null;
    }
}
