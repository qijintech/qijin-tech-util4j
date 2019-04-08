package tech.qijin.util4j.cache.redis;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.Assert;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.utils.MAssert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author michealyang
 * @date 2019/4/3
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class RedisApi {
    private static final TimeUnit Default_Time_Unit = TimeUnit.MILLISECONDS;

    private StringRedisTemplate redisStringTemplate;


    private RedisTemplate<String, Object> redisObjectTemplate;


    public void setRedisStringTemplate(StringRedisTemplate redisStringTemplate) {
        this.redisStringTemplate = redisStringTemplate;
    }

    public void setRedisObjectTemplate(RedisTemplate<String, Object> redisObjectTemplate) {
        this.redisObjectTemplate = redisObjectTemplate;
        redisObjectTemplate.opsForList();
    }

    @PostConstruct
    public void init() {
        RedisUtil.setRedisApi(this);
    }


    /**
     * 指定缓存失效时间
     * 单位时间为毫秒
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public void setExpire(String key, long time) {
        setExpire(key, time, Default_Time_Unit);
    }

    /**
     * 设置key的时间
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void setExpire(String key, long time, TimeUnit timeUnit) {
        redisStringTemplate.expire(key, time, timeUnit);
    }

    /**
     * 根据key 获取过期时间
     * 单位MILLISECONDS
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return getExpire(key, Default_Time_Unit);
    }

    public long getExpire(String key, TimeUnit timeUnit) {
        return redisStringTemplate.getExpire(key, timeUnit);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisStringTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除对象
     */
    @SuppressWarnings("unchecked")
    public long delete(List<String> keys) {
        return redisStringTemplate.delete(keys);
    }

    /**
     * 删除对象
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        return redisStringTemplate.delete(key);
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        return key == null ? null : redisStringTemplate.opsForValue().get(key);
    }


    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time等于0 将设置无限期，不能设置小于0
     * @return true成功 false 失败
     */
    public void setString(String key, String value, long time, TimeUnit timeUnit) {
        redisStringTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 一个不超时的set
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        redisStringTemplate.opsForValue().set(key, value);
    }

    /**
     * 支持存储long类型数据
     *
     * @param key
     * @param value
     * @param milliseconds
     */
    public void set(String key, Long value, long milliseconds) {
        setString(key, String.valueOf(value), milliseconds);
    }

    public void set(String key, Long value) {
        setString(key, String.valueOf(value));
    }

    /**
     * 支持获取long类型数据
     *
     * @param key
     * @return
     */
    public Long get(String key) {
        String value = getString(key);
        return StringUtils.isBlank(value) ? null : Long.valueOf(value);
    }

    /**
     * 保存String
     *
     * @param key
     * @param value
     * @param milliseconds
     */
    public void setString(String key, String value, long milliseconds) {
        setString(key, value, milliseconds, TimeUnit.MILLISECONDS);
    }


    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisStringTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key 键
     * @return
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisStringTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key   键 不能为null
     * @param field 项 不能为null
     * @return 值
     */
    public Object hGet(String key, String field) {
        return redisStringTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值 Map<String, String>
     */
    public Map<Object, Object> hmGet(String key) {
        return redisStringTemplate.opsForHash().entries(key);
    }


    /**
     * HashSet 并设置时间
     *
     * @param key          键
     * @param map          对应多个键值
     * @param milliseconds 时间毫秒
     * @return true成功 false失败
     */
    public void hmSet(String key, Map<String, String> map, long milliseconds) {
        redisStringTemplate.opsForHash().putAll(key, map);
        if (milliseconds > 0) {
            setExpire(key, milliseconds);
        }
    }

    /**
     * 增加hash的指定field
     *
     * @param key
     * @param member
     * @param value
     * @return
     */
    public Long hIncrBy(String key, String member, long value) {
        return redisStringTemplate.opsForHash().increment(key, member, value);
    }


    /**
     * 批量增加hash每个field的值，并返回最新值
     *
     * @param key
     * @param value
     * @return
     */
    public Map<String, Long> hmIncrBy(String key, Map<String, Long> value) {
        List<String> keys = new ArrayList<>();
        List<Object> results = redisStringTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
            value.forEach((k, v) -> {
                keys.add(k);
                stringRedisConn.hIncrBy(key, k, v);
            });
            return null;
        });

        Map<String, Long> ret = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            ret.put(keys.get(i), (Long) results.get(i));
        }
        return ret;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param field 项
     * @param value 值
     * @return true 成功 false失败
     */
    public void hSet(String key, String field, String value) {
        redisStringTemplate.opsForHash().put(key, field, value);
    }


    public boolean setIfAbsent(String key, String value, long milliseconds) {
        boolean result = redisStringTemplate.opsForValue().setIfAbsent(key, value);
        if (result) {
            setExpire(key, milliseconds);
        }
        return result;
    }

    /**
     * 存对象
     *
     * @param key
     * @param object
     * @param milliseconds
     * @param timeUnit
     */
    public void saveObject(String key, Object object, int milliseconds, TimeUnit timeUnit) {
        redisObjectTemplate.opsForValue().set(key, object, milliseconds, timeUnit);
    }


    public void saveObject(String key, Object object) {
        redisObjectTemplate.opsForValue().set(key, object);
    }


    public void saveObject(String key, Object object, int milliseconds) {
        saveObject(key, object, milliseconds, Default_Time_Unit);
    }


    public Boolean saveObjectIfKeyAbsent(String key, Object object, int time, TimeUnit timeUnit) {
        Boolean result = redisObjectTemplate.opsForValue().setIfAbsent(key, object);

        if (Boolean.TRUE.equals(result)) {
            setExpire(key, time, timeUnit);
        }

        return result;
    }

    /**
     * 设置bitmap值
     *
     * @param key
     * @param offset
     * @param on
     */
    public void setBit(String key, long offset, boolean on) {
        redisObjectTemplate.opsForValue().setBit(key, offset, on);
    }

    /**
     * 取bitmap值
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {
        return redisObjectTemplate.opsForValue().getBit(key, offset);
    }

    public Long bitCount(String key) {
        byte[] rawKey = rawKey(redisObjectTemplate, key);
        return redisObjectTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(rawKey));
    }

    /**
     * bitop and
     *
     * @param desKey
     * @param keys
     * @return
     */
    public Long bitOpAnd(String desKey, String... keys) {
        check(desKey, keys);

        byte[] desRawKey = rawKey(redisObjectTemplate, desKey);
        byte[][] rawKeys = rawKeys(redisObjectTemplate, keys);
        return redisObjectTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitOp(RedisStringCommands.BitOperation.AND, desRawKey, rawKeys));
    }

    /**
     * bitop or
     *
     * @param desKey
     * @param keys
     * @return
     */
    public Long bitOpOr(String desKey, String... keys) {
        check(desKey, keys);

        byte[] desRawKey = rawKey(redisObjectTemplate, desKey);
        byte[][] rawKeys = rawKeys(redisObjectTemplate, keys);
        return redisObjectTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitOp(RedisStringCommands.BitOperation.OR, desRawKey, rawKeys));
    }

    /**
     * bitop xor
     *
     * @param desKey
     * @param keys
     * @return
     */
    public Long bitOpXor(String desKey, String... keys) {
        check(desKey, keys);

        byte[] desRawKey = rawKey(redisObjectTemplate, desKey);
        byte[][] rawKeys = rawKeys(redisObjectTemplate, keys);
        return redisObjectTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitOp(RedisStringCommands.BitOperation.XOR, desRawKey, rawKeys));
    }

    private byte[][] rawKeys(RedisTemplate redisTemplate, Object[] keys) {
        byte[][] rawKeys = new byte[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            byte[] rawKey = rawKey(redisObjectTemplate, key);
            rawKeys[i] = rawKey;
        }
        return rawKeys;
    }

    private byte[] rawKey(RedisTemplate redisTemplate, Object key) {
        Assert.notNull(key, "non null key required");
        if (redisTemplate.getKeySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return redisTemplate.getKeySerializer().serialize(key);
    }

    private void check(String desKey, String... keys) {
        MAssert.isTrue(StringUtils.isNotBlank(desKey), ResEnum.INVALID_PARAM);
        MAssert.isTrue(keys != null && keys.length > 0, ResEnum.INVALID_PARAM);
    }

    /**
     * 取对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObject(String key) {
        return (T) redisObjectTemplate.opsForValue().get(key);
    }


    /**
     * 向表头插入元素
     *
     * @param key
     * @param value
     * @return
     */
    public long lPush(String key, String value) {
        return redisStringTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向队尾插入数据
     *
     * @param key
     * @param value
     * @return
     */
    public long rPush(String key, String value) {
        return redisStringTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向表头插入多个元素
     *
     * @param key
     * @param values
     * @return
     */
    public long lPush(String key, Collection<String> values) {
        return redisStringTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * redis LRANGE
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lRange(String key, Long start, Long end) {
        return redisStringTemplate.opsForList().range(key, start, end);
    }

    /**
     * 从表尾弹出元素
     *
     * @param key
     * @return
     */
    public String rPop(String key) {
        return redisStringTemplate.opsForList().rightPop(key);
    }

    /**
     * 返回index处的元素
     *
     * @param key
     * @param index
     * @return
     */
    public String lIndex(String key, long index) {
        return redisStringTemplate.opsForList().index(key, index);
    }

    /**
     * 计算列表长度
     *
     * @param key
     * @return
     */
    public long lLen(String key) {
        return redisStringTemplate.opsForList().size(key);
    }

    /**
     * 对列表进行修剪
     *
     * @param key
     * @param start
     * @param stop
     */
    public void lTrim(String key, long start, long stop) {
        redisStringTemplate.opsForList().trim(key, start, stop);
    }

    /**
     * 向集合添加多个元素
     *
     * @param key
     * @param members
     * @return
     */
    public long sAdd(String key, String... members) {
        return redisStringTemplate.opsForSet().add(key, members);
    }

    /**
     * 向set中添加多个Object元素
     *
     * @param key
     * @param members
     * @return
     */
    public <T extends Serializable> long sAddObject(String key, T... members) {
        return redisObjectTemplate.opsForSet().add(key, members);
    }

    /**
     * 从Set中随机取count个元素
     *
     * @param key
     * @param count
     * @return
     */
    public Set<Object> sRandObject(String key, long count) {
        return redisObjectTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 向集合添加多个元素
     *
     * @param key
     * @param members
     * @return
     */
    public long sAddAsList(String key, List<? extends Object> members) {
        String[] arr = members.stream()
                .map(Object::toString)
                .collect(Collectors.toList())
                .toArray(new String[members.size()]);
        return redisStringTemplate.opsForSet().add(key, arr);
    }

    /**
     * 判断元素是否是集合的成员
     *
     * @param key
     * @param member
     * @return
     */
    public boolean sMember(String key, Object member) {
        return redisStringTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 返回集合中元素个数
     *
     * @param key
     * @return
     */
    public long sCard(String key) {
        return redisStringTemplate.opsForSet().size(key);
    }

    /**
     * 返回集合中全部元素
     *
     * @param key
     * @return
     */
    public Set<String> sMembers(String key) {
        return redisStringTemplate.opsForSet().members(key);
    }

    /**
     * 随机返回N个不同元素
     *
     * @param key
     * @param count
     * @return
     */
    public List<String> sRandmember(String key, long count) {
        return redisStringTemplate.opsForSet().randomMembers(key, count);
    }

    public Set<Object> sMembersObject(String key) {
        return redisObjectTemplate.opsForSet().members(key);
    }

    /**
     * 删除制定元素
     *
     * @param key
     * @param members
     * @return
     */
    public long sRem(String key, Object... members) {
        return redisStringTemplate.opsForSet().remove(key, members);
    }


    /**
     * 向有序集合添加元素
     *
     * @param key
     * @param member
     * @param score
     * @return
     */
    public boolean zAdd(String key, String member, double score) {
        return redisStringTemplate.opsForZSet().add(key, member, score);
    }

    /**
     * 返回有序集合元素个数
     *
     * @param key
     * @return
     */
    public long zCard(String key) {
        return redisStringTemplate.opsForZSet().size(key);
    }

    /**
     * 给制定成员增加分支
     *
     * @param key
     * @param member
     * @param incrScore
     * @return
     */
    public double zIncrby(String key, String member, double incrScore) {
        return redisStringTemplate.opsForZSet().incrementScore(key, member, incrScore);
    }

    /**
     * 增加成员的分数，并返回最新排名
     *
     * @param key
     * @param member
     * @param incr
     * @return {分数(Double), 排名(倒序)(Long)}
     */
    public List<Object> zIncrByWithNewRank(String key, String member, double incr) {
        return redisStringTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
            stringRedisConn.zIncrBy(key, incr, member);
            stringRedisConn.zRevRank(key, member);
            return null;
        });
    }


    /**
     * 返回制定分值范围内的元素，有序集合按照从小到大排序
     *
     * @param key
     * @param fromScore
     * @param toScore
     * @return
     */
    public Set<String> zRangebyscore(String key, double fromScore, double toScore) {
        return redisStringTemplate.opsForZSet().rangeByScore(key, fromScore, toScore);
    }

    /**
     * 返回制定分值范围内的元素，有序集合按照从大到小排序
     *
     * @param key
     * @param fromScore
     * @param toScore
     * @return
     */
    public Set<String> zRevrangebyscore(String key, double fromScore, double toScore) {
        return redisStringTemplate.opsForZSet().reverseRangeByScore(key, fromScore, toScore);
    }


    /**
     * 获取指定排行范围的元素；排行由小到大
     *
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeWithScore(String key, long start, long stop) {
        return redisStringTemplate.opsForZSet().rangeWithScores(key, start, stop);
    }

    /**
     * 获取指定排行范围的元素；排行由大到小
     *
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScore(String key, long start, long stop) {
        return redisStringTemplate.opsForZSet().reverseRangeWithScores(key, start, stop);
    }

    /**
     * 分页获取指定分数范围的元素；排行由小到大
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisStringTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 分页获取指定分数范围的元素；排行由大到小
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisStringTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }


    /**
     * 返回指定成员的分支
     *
     * @param key
     * @param member
     * @return
     */
    public double aScore(String key, Object member) {
        return redisStringTemplate.opsForZSet().score(key, member);
    }

    /**
     * 获取指定成员的排名
     *
     * @param key
     * @param member
     * @return
     */
    public Long zRank(String key, Object member) {
        return redisStringTemplate.opsForZSet().rank(key, member);
    }

    /**
     * 同时获取指定成员的排名和分数
     *
     * @param key
     * @param member
     * @return {分数(Double), 排名(倒序)(Long)}
     */
    public List<Object> zRankScore(String key, String member) {
        return redisStringTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
            stringRedisConn.zScore(key, member);
            stringRedisConn.zRevRank(key, member);
            return null;
        });
    }

    /**
     * 批量获取kv
     *
     * @param keys
     * @return List<String>
     */
    public List<Object> get(Collection<String> keys) {
        return redisStringTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;

            keys.forEach(id -> {
                stringRedisConn.get(id);
            });
            return null;
        });
    }

    /**
     * 批量获取hash
     *
     * @param keys
     * @return List<Map < String ,   String>>
     */
    public List<Object> hmGet(Collection<String> keys) {
        return redisStringTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;

            keys.forEach(id -> {
                stringRedisConn.hGetAll(id);
            });
            return null;
        });
    }

    private static final String LUA_CAS =
            "if (ARGV[1] == '' or redis.call('get',KEYS[1]) == ARGV[1]) then" +
                    "    redis.call('set',KEYS[1],ARGV[2]); " +
                    "end;" +
                    "return tonumber(redis.call('get',KEYS[1]))";

    /**
     * 当前值相同则设置新值
     *
     * @param key
     * @param current
     * @param next
     * @return Long
     */
    public Long compareAndSet(String key, Long current, Long next) {
        return execute(Long.class, LUA_CAS, Arrays.asList(new String[]{key}), current != null ? current.toString() : "", next != null ? next.toString() : "");
    }

    private static final String LUA_LPUSH_LTRIM =
            "local limit = tonumber(ARGV[1]);" +
                    "table.remove(ARGV, 1);" +
                    "redis.call('lpush', KEYS[1], unpack(ARGV));" +
                    "redis.call('ltrim', KEYS[1], 0, limit - 1);" +
                    "return redis.call('lrange', KEYS[1], 0, -1);";

    /**
     * 向队列头插入元素；并限制队列大小
     *
     * @param key
     * @param limit
     * @param args  左右顺序和队列中一致
     * @return 队列中所有元素
     */
    public List<String> lPush(String key, Long limit, String... args) {
        ArrayUtils.reverse(args);
        args = ArrayUtils.insert(0, args, limit.toString());
        return execute(List.class, LUA_LPUSH_LTRIM, Arrays.asList(new String[]{key}), args);
    }

    /**
     * 执行lua脚本
     *
     * @param luaText
     * @param keys
     * @param args
     * @return Long
     */
    public <T> T execute(Class<T> clazz, String luaText, List<String> keys, String... args) {
        DefaultRedisScript<T> redisScript = new DefaultRedisScript<>(luaText);
        redisScript.setResultType(clazz);
        return redisStringTemplate.execute(redisScript, keys, args);
    }

    /**
     * 执行lua脚本
     *
     * @param lua
     * @param keys
     * @param args
     * @return
     */
    public Object execute(String lua, List<String> keys, Object... args) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource(lua));
        redisScript.setResultType(Long.class);
        return redisObjectTemplate.execute(redisScript, keys, args);
    }
}
