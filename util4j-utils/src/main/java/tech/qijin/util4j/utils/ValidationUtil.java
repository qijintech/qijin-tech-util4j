package tech.qijin.util4j.utils;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.routines.EmailValidator;

import tech.qijin.util4j.lang.constant.ResEnum;

/**
 * @author michealyang
 * @date 2019/1/14
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class ValidationUtil {

    private static String phoneNoRegex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

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
     * 校验参数的合法性
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Boolean isValid(T t) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validationSet = validator.validate(t);
        if (CollectionUtils.isNotEmpty(validationSet)) {
        }
        return CollectionUtils.isEmpty(validationSet);
    }

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
        if (CollectionUtils.isNotEmpty(validationSet)) {
            StringBuilder sb = new StringBuilder();
            validationSet.stream().forEach(
                    validation -> sb.append(validation.getMessage()).append(";"));
            MAssert.isTrue(false, ResEnum.INVALID_PARAM.code, sb.toString());
        }
    }

    public static boolean password(String password) {
        MAssert.notBlank(password, ResEnum.INVALID_PARAM);
        return password.matches(phoneNoRegex);
    }

    /**
     * 判断是否是手机号
     *
     * @param value
     * @return
     */
    public static boolean matchPhoneNo(String value) {
        if (value.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile(phoneNoRegex);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 校验手机号的合法性
     *
     * @param phoneNo
     */
    public static void validatePhoneNo(String phoneNo) {
        MAssert.isTrue(phoneNo.length() == 11, ResEnum.INVALID_PARAM.code, "手机号应为11位数");
        Pattern p = Pattern.compile(phoneNoRegex);
        Matcher m = p.matcher(phoneNo);
        MAssert.isTrue(m.matches(), ResEnum.INVALID_PARAM.code, "请输入正确的手机号");
    }

    /**
     * 判断是否是邮箱
     *
     * @param value
     * @return
     */
    public static boolean matchEmail(String value) {
        return EmailValidator.getInstance().isValid(value);
    }

    /**
     * 校验email的合法性
     *
     * @param email
     */
    public static void validateEmail(String email) {
        MAssert.isTrue(EmailValidator.getInstance().isValid(email), ResEnum.INVALID_EMAIL);
    }
}
