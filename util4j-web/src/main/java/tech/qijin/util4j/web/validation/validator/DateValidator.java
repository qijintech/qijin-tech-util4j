package tech.qijin.util4j.web.validation.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;
import tech.qijin.util4j.web.validation.annotation.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.text.SimpleDateFormat;

import static tech.qijin.util4j.web.common.Constants.*;

/**
 * @author michealyang
 * @date 2019-11-07
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@AutoConfigureOrder
@Component
public class DateValidator implements ConstraintValidator<Date, String> {
    private Date dateAnnotation;

    @Override
    public void initialize(Date constraintAnnotation) {
        dateAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        SimpleDateFormat sf = new SimpleDateFormat(dateAnnotation.format());
        try {
            sf.parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
