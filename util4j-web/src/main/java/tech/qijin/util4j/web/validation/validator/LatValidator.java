package tech.qijin.util4j.web.validation.validator;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.web.validation.annotation.IPV4;
import tech.qijin.util4j.web.validation.annotation.Lat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019-11-17
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Component
public class LatValidator implements ConstraintValidator<Lat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Pattern p = Pattern.compile("^[\\-\\+]?([0-8]?\\d{1}|[0-8]?\\d{1}\\.\\d{1,15}|90|90\\.0{1,15})$");
        return p.matcher(value).matches();
    }

    @Override
    public void initialize(Lat constraintAnnotation) {

    }
}
