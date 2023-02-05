package tech.qijin.util4j.web.pojo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import tech.qijin.util4j.utils.ConvertUtil;
import tech.qijin.util4j.utils.DateUtil;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LocalCacheAgent<T> {
    private Date lastUpdateAt = Date.from(Instant.EPOCH);
    private List<T> data = Lists.newArrayList();
    private volatile Map<Long, T> map = Maps.newHashMap();
    private boolean update;

    // 间隔时间，默认5s
    private Integer intervalSeconds = 5;

    public LocalCacheAgent() {
    }

    public LocalCacheAgent(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public LocalCacheAgent<T> process(Supplier<Date> lastUpdate, Supplier<List<T>> queryFromDB) {
        Date updateAt = lastUpdate.get();
        if (null == updateAt
                || updateAt.after(lastUpdateAt)
                || Math.abs(DateUtil.getMinusSeconds(DateUtil.now(), updateAt).longValue()) < intervalSeconds) {
            if (updateAt != null) {
                lastUpdateAt = updateAt;
            }
            data = queryFromDB.get();
            map = Maps.newHashMap();
            update = true;
        }
        return this;
    }

    public void callback(Consumer<List<T>> consumer) {
        if (!update) return;
        update = false;
        consumer.accept(data);
    }

    public List<T> get() {
        return data;
    }

    public Map<Long, T> map(Function<? super T, Long> keyMapper) {
        if (MapUtils.isNotEmpty(map)) return map;
        synchronized (this) {
            if (MapUtils.isNotEmpty(map)) return map;
            map = this.data.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
        }
        return map;
    }

    public List<T> clone(Class<T> clazz) {
        return ConvertUtil.convertList(data, clazz);
    }

}
