package tech.qijin.util4j.practice.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author michealyang
 * @date 2019/3/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(MyCondition.class)
public @interface MyConditionalOnProperty {
    String prefix() default "";

    String name() default "";

    String value() default "";

    String contains() default "";
}
