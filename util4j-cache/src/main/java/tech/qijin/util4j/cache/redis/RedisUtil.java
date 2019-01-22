package tech.qijin.util4j.cache.redis;


import org.apache.commons.lang3.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import tech.qijin.util4j.cache.util.ConfigUtil;

import java.io.Serializable;


/**
 * @author michealyang
 * @date 2019/1/11
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RedisUtil {
    private static JedisPool pool = new JedisPool(new JedisPoolConfig(),
            ConfigUtil.get("host", "127.0.0.1"), //redis host
            Integer.valueOf(ConfigUtil.get("port", "6379")),    //redis port
            Integer.valueOf(ConfigUtil.get("timeout", "1000")),     //timeout
            ConfigUtil.get("password", "None"), //redis password
            ConfigUtil.get("ssl"));
    private static final String OK = "OK";

    /**
     * @param key
     * @param value
     * @param expire 毫秒
     * @return
     */
    public static boolean setnx(String key, Object value, Long expire) {
        return true;
    }

    public static boolean set(String key, String value) {
        return OK.equals(pool.getResource().set(key, value));
    }

    public static boolean set(String key, Long value) {
        return set(key, String.valueOf(value));
    }

    public static boolean set(String key, Integer value) {
        return set(key, String.valueOf(value));
    }

    public static boolean setObject(String key, Serializable object) {
        return OK.equals(pool.getResource().set(SerializationUtils.serialize(key),
                SerializationUtils.serialize(object)));
    }

    public static Object getObject(String key) {
        byte[] object = pool.getResource().get(SerializationUtils.serialize(key));
        if (object == null) {
            return null;
        }
        return SerializationUtils.deserialize(object);
    }

    public static String get(String key) {
        return pool.getResource().get(key);
    }

    public static boolean compareAndSet(String key, Long old, Long timestamp) {
        return true;
    }

    public static Long del(String key) {
        Jedis jedis = pool.getResource();
        Long num = jedis.del(key);
        if (num == null || num <= 0) {
            return jedis.del(SerializationUtils.serialize(key));
        } else {
            return num;
        }
    }
}
