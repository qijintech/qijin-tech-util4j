package tech.qijin.util4j.web.validation.annotation;

import tech.qijin.util4j.web.validation.validator.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static tech.qijin.util4j.lang.constant.Const.DATE_FORMAT_DASHED_S;


/**
 * @author michealyang
 * @date 2019-11-07
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
@Documented
@Constraint(validatedBy = DateValidator.class)//指明校验逻辑的类
public @interface Date {
    String format() default DATE_FORMAT_DASHED_S;

    String message() default "invalid date format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
