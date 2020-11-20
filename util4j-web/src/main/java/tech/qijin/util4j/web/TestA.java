package tech.qijin.util4j.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //用{}包围，逗号分隔
@Retention(RetentionPolicy.RUNTIME)
public @interface TestA {
    String value();

    boolean autoRefreshed() default false;
}
