package tech.qijin.util4j.web.validation.validator;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.web.validation.annotation.IPV4;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019-11-17
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Component
public class IPV4Validator implements ConstraintValidator<IPV4, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Pattern p = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
        return p.matcher(value).matches();
    }

    @Override
    public void initialize(IPV4 constraintAnnotation) {

    }
}
