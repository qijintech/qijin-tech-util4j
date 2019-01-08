package tech.qijin.util4j.utils;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/15
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class NumberUtil {
    /**
     * 判断是否为null或==0
     *
     * @param value
     * @return
     */
    public static boolean nullOrZero(Number value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return ((Comparable) value).compareTo(0) == 0;
        }
        if (value instanceof Long) {
            return ((Comparable) value).compareTo(0L) == 0;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 是否为null或者<=0
     *
     * @param value
     * @return
     */
    public static boolean nullOrLeZero(Number value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return ((Comparable) value).compareTo(0) < 0;
        }
        if (value instanceof Long) {
            return ((Comparable) value).compareTo(0L) < 0;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 是否大于0
     *
     * @param value
     * @return
     */
    public static boolean gtZero(Number value) {
        return !nullOrLeZero(value);
    }

    /**
     * 判断是否为null或<=0
     *
     * @param value
     * @return
     */
    public static boolean nullOrLtZero(Number value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Integer) {
            return ((Comparable) value).compareTo(0) <= 0;
        }
        if (value instanceof Long) {
            return ((Comparable) value).compareTo(0L) <= 0;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 是否大于等于0
     *
     * @param value
     * @return
     */
    public static boolean geZero(Number value) {
        return !nullOrLtZero(value);
    }
}
