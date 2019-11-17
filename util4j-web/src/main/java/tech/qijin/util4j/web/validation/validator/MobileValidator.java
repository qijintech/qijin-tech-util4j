package tech.qijin.util4j.web.validation.validator;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.web.validation.annotation.IPV6;
import tech.qijin.util4j.web.validation.annotation.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019-11-17
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Component
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        return p.matcher(value).matches();
    }

    @Override
    public void initialize(Mobile constraintAnnotation) {

    }
}
