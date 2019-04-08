package tech.qijin.util4j.aop.advice;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer: Tutorials:: http://www.springboottutorial.com/spring-boot-and-aop-with-spring-boot-starter-aop
 **/
public class CommonPointcut {
    @Pointcut("@annotation(tech.qijin.util4j.aop.annotation.Retry)")
    public void retryAnnotationPointcut() {
    }

    @Pointcut("@annotation(tech.qijin.util4j.aop.annotation.Timed)")
    public void timedAnnotationPointcut() {
    }

    @Pointcut("@annotation(tech.qijin.util4j.aop.annotation.Cas)")
    public void casAnnotationPointcut() {
    }

    @Pointcut("@annotation(tech.qijin.util4j.aop.annotation.Log)")
    public void logAnnotationPointcut() {
    }

    @Pointcut("@annotation(tech.qijin.util4j.aop.annotation.ListCheck)")
    public void listCheckAnnotationPointcut() {
    }

//    @Pointcut("execution(public * tech.qijin.util4j..services.*.*(..))")
//    public void dubboProviderPointcut() {
//    }
}
