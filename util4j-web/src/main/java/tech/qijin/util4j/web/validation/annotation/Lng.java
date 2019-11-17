package tech.qijin.util4j.web.validation.annotation;

import tech.qijin.util4j.web.validation.validator.LngValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * @author michealyang
 * @date 2019-11-07
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
@Documented
@Constraint(validatedBy = {LngValidator.class})
//@Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}|0?\\d{1,2}\\.\\d{1,15}|1[0-7]?\\d{1}|1[0-7]?\\d{1}\\.\\d{1,15}|180|180\\.0{1,15})$")
public @interface Lng {
    String message() default "invalid latitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
