package tech.qijin.util4j.cache.redis;


/**
 * @author michealyang
 * @date 2019/1/11
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RedisUtil {
    private volatile static RedisApi redisApi;

    public static void setRedisApi(RedisApi api) {
        redisApi = api;
    }
}
