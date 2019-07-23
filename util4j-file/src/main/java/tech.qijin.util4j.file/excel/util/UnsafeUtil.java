package tech.qijin.util4j.file.excel.util;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 获取Unsafe类
 *
 * @author michealyang
 * @version 1.0
 * @created 18/6/11
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class UnsafeUtil {
    public static Unsafe getUnsafe() {
        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("获取Unsafe对象失败");
        }
        f.setAccessible(true);
        try {
            Unsafe unsafe = (Unsafe) f.get(null);
            return unsafe;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("获取Unsafe对象失败");
        }
    }
}