package tech.qijin.util4j.cache;

import tech.qijin.util4j.cache.redis.RedisUtil;

import java.util.Optional;

/**
 * 使用redis来实现分布式锁，是不可能实现的。所有不能保证强一致性的分布式锁，都不是真正的分布式锁。
 * <p>
 * 需要应对的情况
 * <ul>
 * <li>场景1: 线程A获取锁成功后，突然宕机，导致永远锁等待。</li>
 * <li>解决办法: 可通过设置expire时间来避免永远锁等待</li>
 * <li>场景2: 线程A获取锁成功，但是由于GC导致锁超时，线程B又获取了锁。</li>
 * <li>解决办法: 这种情况可以通过设置较长时间的expire来避免</li>
 * <li>场景3: 极端情况下，场景2没有能成功避免重复获取锁，线程A恢复后，释放了B的锁。</li>
 * <li>解决办法: 解决办法是set一个value值，释放锁只能释放自己的。注意，这个需要使用lua脚本保证原子性。</li>
 * <li>场景4: redis master宕机导致主从切换，原来的锁失效，导致重复获取锁。</li>
 * <li>解决办法: 只能使用RedLock了</li>
 * </ul>
 * </p>
 *
 * @author michealyang
 * @date 2019/1/11
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class DLock {
    /**
     * 缓存过期时间，单位为毫秒
     */
    private static final Long EXPIRE = 1500L;

    public static Optional<Long> lock(String key) {
        Long timestamp = System.currentTimeMillis();
        if (RedisUtil.setnx(key, timestamp, EXPIRE)) {
            return Optional.of(timestamp);
        }
        return Optional.empty();
    }

    public static Optional<Long> lockLoop(String key) {
        return null;
    }

    public static boolean unlock(String key) {
        return false;
    }
}
