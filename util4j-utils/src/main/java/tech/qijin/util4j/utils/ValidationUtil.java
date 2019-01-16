package tech.qijin.util4j.utils;

import tech.qijin.util4j.lang.constant.ResEnum;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author michealyang
 * @date 2019/1/14
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class ValidationUtil {

    /**
     * 密码强度校验
     * <p>
     * <ul>
     * <li>不能全是数字</li>
     * <li>不能全是字母</li>
     * <li>必须是数字和字母，不能有其他字符</li>
     * </ul>
     * </p>
     */
    private static final String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    /**
     * 校验参数的合法性。
     * <p>需要结合javax validation提供的注解使用，如@NotNull, @NotEmpty</p>
     *
     * @param t
     * @param <T>
     * @see javax.validation
     */
    public static <T> void validate(T t) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validationSet = validator.validate(t);
        validationSet.stream().forEach(
                validation -> MAssert.isTrue(false, ResEnum.INVALID_PARAM.code, validation.getMessage()));
    }

    public static boolean password(String password) {
        MAssert.notBlank(password, ResEnum.INVALID_PARAM);
        return password.matches(regex);
    }
}
