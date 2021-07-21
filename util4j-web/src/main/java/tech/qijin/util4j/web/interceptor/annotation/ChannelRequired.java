package tech.qijin.util4j.web.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加在Method上面，用来标识请求header中需要添加
 *
 * @author michealyang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelRequired {
    boolean required() default true;
}
