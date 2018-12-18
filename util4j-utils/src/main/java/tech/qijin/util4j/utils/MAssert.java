package tech.qijin.util4j.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.exception.ValidateException;

import java.util.Collection;

public class MAssert {

    public static void isTrue(boolean expression, String errorMessage) {
        MAssert.isTrue(expression, ResEnum.BAD_REQUEST.code, errorMessage);
    }

    public static void isTrue(boolean expression, int code, String errorMessage) {
        if (!expression) {
            throw new ValidateException(code, errorMessage);
        }
    }

    public static void isTrue(boolean expression, ResEnum resEnum) {
        MAssert.isTrue(expression, resEnum.code, resEnum.msg);
    }

    public static void isNull(Object object, String message) {
        MAssert.isNull(object, ResEnum.BAD_REQUEST.code, message);
    }

    public static void isNull(Object object, int code, String message) {
        MAssert.isTrue(object == null, code, message);
    }

    public static void isNull(Object object, ResEnum resEnum) {
        MAssert.isNull(object, resEnum.code, resEnum.msg);
    }

    public static void notNull(Object object, String message) {
        MAssert.notNull(object, ResEnum.BAD_REQUEST.code, message);
    }

    public static void notNull(Object object, int code, String message) {
        MAssert.isTrue(object != null, code, message);
    }

    public static void notNull(Object object, ResEnum resEnum) {
        MAssert.notNull(object, resEnum.code, resEnum.msg);
    }

    public static <T> void notEmpty(Collection<T> collection, String message) {
        MAssert.notEmpty(collection, ResEnum.BAD_REQUEST.code, message);
    }

    public static <T> void notEmpty(Collection<T> collection, ResEnum resEnum) {
        notEmpty(collection, resEnum.code, resEnum.msg);
    }

    public static <T> void isEmpty(Collection<T> collection, String message) {
        MAssert.isTrue(CollectionUtils.isEmpty(collection), ResEnum.BAD_REQUEST.code, message);
    }

    public static <T> void notEmpty(Collection<T> collection, int code, String message) {
        MAssert.isTrue(CollectionUtils.isNotEmpty(collection), code, message);
    }

    public static void notBlank(String str, String message) {
        MAssert.isTrue(!StringUtils.isBlank(str), ResEnum.BAD_REQUEST.code, message);
    }

    public static void notBlank(String str, ResEnum resEnum) {
        MAssert.isTrue(!StringUtils.isBlank(str), resEnum.code, resEnum.msg);
    }

    public static void lengthBetween(String content, Integer minLength, Integer maxLength, String message) {
        MAssert.notBlank(content, message);
        MAssert.isTrue(content.length() >= minLength && content.length() <= maxLength, message);
    }


}
