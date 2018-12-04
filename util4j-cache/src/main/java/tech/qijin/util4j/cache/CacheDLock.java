package tech.qijin.util4j.cache;

/**
 * 缓存实现的分布式锁
 *
 * @author michealyang
 * @date 2018/11/30
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class CacheDLock {
    public static boolean lock(String key) {
        return false;
    }

    public static boolean tryLock(String key) {
        return false;
    }

    public static boolean unlock(String key) {
        return false;
    }
}
