package tech.qijin.util4j.cache.redis;


/**
 * @author michealyang
 * @date 2019/1/11
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RedisUtil {
    /**
     *
     * @param key
     * @param value
     * @param expire 毫秒
     * @return
     */
    public static boolean setnx(String key, Object value, Long expire) {
        return true;
    }

    public static Object get(String key) {
        return null;
    }

    public static boolean compareAndSet(String key, Long old, Long timestamp) {
        return true;
    }

    public static boolean delete(String key) {
        return true;
    }
}
