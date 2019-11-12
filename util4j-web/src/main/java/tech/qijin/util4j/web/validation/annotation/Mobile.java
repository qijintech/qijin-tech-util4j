package tech.qijin.util4j.web.validation.annotation;

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
@Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$")
@Constraint(validatedBy = {})//指明校验逻辑的类
public @interface Mobile {
    String message() default "invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
