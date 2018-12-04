package tech.qijin.util4j.utils.assign;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class AssignUtil {
    public static <T> T assign(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
