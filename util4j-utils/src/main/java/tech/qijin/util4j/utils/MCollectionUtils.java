package tech.qijin.util4j.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * CollectionUtils扩展
 *
 * @author michealyang
 * @date 2018/12/18
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class MCollectionUtils {
    /**
     * 将一个大的list分隔成大小最大为size的多个list
     *
     * @param list
     * @param size
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, final int size) {
        List<List<T>> parts = Lists.newArrayList();
        final int n = list.size();
        for (int i = 0; i < n; i += size) {
            parts.add(Lists.newArrayList(list.subList(i, Math.min(n, i + size))));
        }
        return parts;
    }
}
