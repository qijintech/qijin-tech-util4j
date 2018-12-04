package tech.qijin.util4j.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制seconds秒内只能请求1次
 * <p>需要结合cache使用</p>
 * <p>需要userId</p>
 *
 * @author michealyang
 * @date 2018/11/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    //默认5s内只能访问一次
    int seconds() default 5;
}
