package tech.qijin.util4j.cache;

import lombok.extern.slf4j.Slf4j;

/**
 * 缓存实现的分布式锁
 *
 * @author michealyang
 * @date 2018/11/30
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class CacheDLock {


    public static boolean tryLock(String key, Long lockExpireMS) {

        boolean result = CacheUtil.setnx(key, System.currentTimeMillis());

        log.info("[Redis Lock] try lock. key:{}, result:{}", key, result);

        if (result == false) {
            // 防超时功能
            Long lastLock = (Long) CacheUtil.get(key);
            // 可能瞬间有线程删除了锁
            if (lastLock == null) {
                // 重新set一次
                result = CacheUtil.setnx(key, System.currentTimeMillis());

                log.info("[Redis Lock] try lock again. key:{}, result:{}", key, result);
            } else {
                if (lockExpireMS != null) {
                    // 检查老的锁是否过期
                    long now = System.currentTimeMillis();
                    if (lastLock + lockExpireMS < now) {
                        // 已经超时 , 如果true，说明抢到锁，如果false，说明被其线程他抢到锁
                        result = CacheUtil.compareAndSet(key, lastLock, now);

                        log.info("[Redis Lock] lock overtime. relock. key:{}, value:{}, result:{}", key, now, result);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 高频下的分布式锁
     *
     * @param key
     * @param lockExpireMS
     * @return
     */
    public static boolean tryLockForHighFrequency(String key, Long lockExpireMS) {

        boolean result = false;
        long now = System.currentTimeMillis();
        Long lastLock = (Long) CacheUtil.get(key);
        // 高频情况下先查
        if (lastLock == null) {
            // set一次
            result = CacheUtil.setnx(key, now);
        } else {
            if (lockExpireMS != null) {
                // 检查老的锁是否过期
                if (lastLock + lockExpireMS < now) {
                    // 已经超时 , 如果true，说明抢到锁，如果false，说明被其线程他抢到锁
                    result = CacheUtil.compareAndSet(key, lastLock, now);
                }
            }
        }

        return result;
    }


    /**
     * 释放锁，返回以前是否有锁
     *
     * @param key
     * @return
     */
    public static boolean freeLock(String key) {

        boolean result = CacheUtil.setnx(key, System.currentTimeMillis());

        CacheUtil.delete(key);
        log.info("[Redis Lock] free lock. key:{}", key);

        return result;
    }

    public static boolean hasLock(String key) {
        Object lockObj = CacheUtil.get(key);
        return lockObj != null;
    }

}
