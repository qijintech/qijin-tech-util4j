package tech.qijin.util4j.web.validation.validator;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.web.validation.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019-11-14
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private Password passwordAnnotation;

    private static final Pattern middlePattern = Pattern.compile("");
    private static final Pattern higthPattern = Pattern.compile("");
    private static final Pattern strongPattern = Pattern.compile("");

    @Override
    public void initialize(Password constraintAnnotation) {
        passwordAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // 校验密码长度
        if (value.length() == 0 || value.length() < passwordAnnotation.length()) {
            return false;
        }
        switch (passwordAnnotation.strength()) {
            case ANY:
                return any(value);
            case MIDDLE:
                return middle(value);
            case HIGH:
                return high(value);
            case STRONG:
                return strong(value);
        }
        return false;
    }

    private boolean any(String value) {
        return true;
    }

    /**
     * 必须包含数字、字母。字母大小写没有限制
     *
     * @return
     */
    private boolean middle(String value) {
        return middlePattern.matcher(value).matches();
    }

    /**
     * 必须包含数字、小写字母、大写字母
     *
     * @return
     */
    private boolean high(String value) {
        return higthPattern.matcher(value).matches();
    }

    /**
     * 必须包含数字、小写字母、大写字母、特殊字符
     *
     * @return
     */
    private boolean strong(String value) {
        return strongPattern.matcher(value).matches();
    }
}
