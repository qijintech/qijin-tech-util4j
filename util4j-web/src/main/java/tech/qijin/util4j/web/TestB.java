package tech.qijin.util4j.web;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE) //用{}包围，逗号分隔
@Retention(RetentionPolicy.RUNTIME)
@TestA("tt")
public @interface TestB {
    @AliasFor(annotation = TestA.class)
    String value() default "asdf";

    @AliasFor(annotation = TestA.class)
    boolean autoRefreshed() default true;
}
