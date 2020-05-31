package tech.qijin.util4j.web.validation.annotation;

import tech.qijin.util4j.web.common.Constants;
import tech.qijin.util4j.web.validation.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    Constants.PasswdStrength strength() default Constants.PasswdStrength.ANY;

    // 密码最短长度限制, 默认无限制
    int length() default 0;

    String message() default "invalid password format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
