package tech.qijin.util4j.web.validation.validator;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.web.validation.annotation.IPV4;
import tech.qijin.util4j.web.validation.annotation.Lng;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019-11-17
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Component
public class LngValidator implements ConstraintValidator<Lng, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Pattern p = Pattern.compile("^[\\-\\+]?(0?\\d{1,2}|0?\\d{1,2}\\.\\d{1,15}|1[0-7]?\\d{1}|1[0-7]?\\d{1}\\.\\d{1,15}|180|180\\.0{1,15})$");
        return p.matcher(value).matches();
    }

    @Override
    public void initialize(Lng constraintAnnotation) {

    }
}
