package tech.qijin.util4j.utils;

import java.util.List;

import com.google.common.collect.Lists;

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

    /**
     * 给定一个value和一个lists，返回这个value在这个lists相邻元素之间的哪个区间
     *
     * <p>
     * 如lists为[10, 20, 30, 40]
     * 如value<10时，返回结果为0
     * value>=10 && value<20时，返回结果为1
     * value>=20 && value<30时，返回结果为2
     * value>=30 && value<40时，返回结果为3
     * value>40时，返回结果为4
     * </p>
     *
     * @param value
     * @param lists 需要保证lists是递增的
     * @param <T>
     * @return
     */
    public static <T extends Comparable<? super T>> int thresholdIndex(T value, List<T> lists) {
        for (int index = 0; index < lists.size(); index++) {
            if (value.compareTo(lists.get(index)) < 0) {
                return index;
            }
        }
        return lists.size();
    }

    public static void main(String[] args) {
        List<Integer> lists = Lists.newArrayList(10, 20, 30, 40);
        System.out.println(thresholdIndex(3, lists));
        System.out.println(thresholdIndex(13, lists));
        System.out.println(thresholdIndex(23, lists));
        System.out.println(thresholdIndex(33, lists));
        System.out.println(thresholdIndex(43, lists));
    }
}
