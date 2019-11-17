package tech.qijin.util4j.web.validation.annotation;

import tech.qijin.util4j.web.validation.validator.LatValidator;

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
@Constraint(validatedBy = {LatValidator.class})
//@Pattern(regexp = "^[\\-\\+]?([0-8]?\\d{1}|[0-8]?\\d{1}\\.\\d{1,15}|90|90\\.0{1,15})$")
public @interface Lat {
    String message() default "invalid latitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
