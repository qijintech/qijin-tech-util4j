package tech.qijin.util4j.aop.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.qijin.util4j.aop.annotation.Cas;
import tech.qijin.util4j.aop.exception.CasException;
import tech.qijin.util4j.utils.log.LogFormat;

/**
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer: 一个例子：https://blog.csdn.net/dm_vincent/article/details/72851240
 * 另一个例子: https://www.baeldung.com/spring-aop-annotation
 **/
@Aspect
@Component
public class CasAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasAdvice.class);

    @Around("CommonPointcut.casAnnotationPointcut()")
    public Object cas(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Cas cas = signature.getMethod().getDeclaredAnnotation(Cas.class);
        int times = cas.times();
        int interval = cas.interval();

        while (times-- > 0) {
            try {
                LOGGER.info("start cas advance");
                Object result = joinPoint.proceed();
                return result;
            } catch (CasException e) {
                LOGGER.warn(LogFormat.builder().message("cas mode failure")
                        .put("className", className)
                        .put("methodName", methodName)
                        .put("times", times)
                        .build());
                if (interval > 0) {
                    Thread.sleep(interval);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        throw new RuntimeException("data was modified by others");
    }
}
