package tech.qijin.util4j.aop.annotation;

import java.lang.annotation.*;

/**
 * 在需要打印log的函数上，添加该注解
 *
 * @author michealyang
 * @date 2018/12/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Target(ElementType.METHOD) //用{}包围，逗号分隔
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Log {
}
