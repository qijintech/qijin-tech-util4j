package tech.qijin.util4j.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author michealyang
 * @date 2018/11/14
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Target(ElementType.FIELD) //用{}包围，逗号分隔
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
}
