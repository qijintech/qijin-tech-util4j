package tech.qijin.util4j.aop.advice;

import java.util.Collections;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.utils.LogFormat;

/**
 * 在进入函数前，打印log
 *
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Aspect
@Component
@Slf4j
public class LogAdvice {

    @Before("CommonPointcut.logAnnotationPointcut()")
    public void log(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String methodName = signature.getMethod().getName();
        String className = signature.getMethod().getDeclaringClass().getSimpleName();
        String[] argsName = signature.getParameterNames();
        logArgs(className + "." + methodName, argsName, args);
    }

    private void logArgs(String message, String[] argsName, Object[] args) {
        Map<String, Object> map = mappingArgs(argsName, args);
        LogFormat.LogFormatBuilder builder = LogFormat.builder();
        map.entrySet().stream().forEach(entry -> {
            String argName = entry.getKey();
            Object argValue = entry.getValue();
            builder.put(argName, argValue);
        });
        log.info(builder.message(message).build());
    }

    private Map<String, Object> mappingArgs(String[] argsName, Object[] args) {
        if (argsName == null || args == null
                || argsName.length == 0 || args.length == 0) {
            return Collections.emptyMap();
        }
        if (argsName.length != args.length) {
            log.error(LogFormat.builder().message("unmatched args").build());
            return Collections.emptyMap();
        }
        Map<String, Object> map = Maps.newHashMap();
        for (int i = 0; i < argsName.length; i++) {
            String name = argsName[i];
            Object arg = args[i];
            map.put(name, arg);
        }
        return map;
    }
}
