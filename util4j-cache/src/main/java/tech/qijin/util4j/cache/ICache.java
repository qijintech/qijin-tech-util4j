package tech.qijin.util4j.cache;

/**
 * @author michealyang
 * @date 2019/7/19
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface ICache {

    void setLong(String key, Long value);

    void setLong(String key, Long value, long expireSeconds);

    void setString(String key, String value);

    void setString(String key, String value, long expireSeconds);

    void setObject(String key, Object object);

    void setObject(String key, Object object, int expireSeconds);

    Long getLong(String key);

    String getString(String key);

    <T> T getObject(String key);

    /**
     * 指定缓存失效时间
     * 单位时间为毫秒
     *
     * @param key     键
     * @param seconds 时间(秒)
     * @return
     */
    void setExpire(String key, long seconds);

    /**
     * 根据key 获取过期时间
     * 单位MILLISECONDS
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(String key);

    boolean delete(String key);

    boolean deleteObject(String key);

    long incr(String key, long delta);

    long decr(String key, long delta);

    /**
     *  ================== List ===================
     */

    /**
     *  ================== Set ===================
     */

    /**
     *  ================== Hash ===================
     */

    /**
     *  ================== SortedSet ===================
     */

    /**
     *  ================== Bit ===================
     */
}
