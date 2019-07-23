package tech.qijin.util4j.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import tech.qijin.util4j.lang.annotation.FieldCheck;
import tech.qijin.util4j.lang.annotation.Required;
import tech.qijin.util4j.lang.constant.ResEnum;

/**
 * 检查一个Class里面的参数是否为null
 * <p>
 * 带有@Required注解的数据不能为null
 * </p>
 * <p>
 * 数据经缓存后，每次调用会有10ms的性能损失。如在可接受的范围内，可以使用
 * </p>
 * <p>
 * 支持校验父类Field
 * </p>
 *
 * @author michealyang
 * @date 2018/11/14
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class FieldChecker {
    private static Map<String, List<Field>> cache = Maps.newHashMap();

    public static <T> void check(T data) {
        if (data == null) {
            return;
        }
        Class clazz = data.getClass();
        Annotation annotation = clazz.getDeclaredAnnotation(FieldCheck.class);
        if (annotation == null) {
            return;
        }
        List<Field> fields = cache.get(clazz.getName());
        if (fields == null) {
            fields = setAnnotationedFields(clazz);
        }
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        for (Field field : fields) {
            Object fieldValue;
            try {
                field.setAccessible(true);
                fieldValue = field.get(data);
            } catch (IllegalAccessException e) {
                //ignore this kind of exception
                continue;
            }
            MAssert.notNull(fieldValue, ResEnum.INVALID_PARAM.code, field.getName() + " should not be null");
        }
    }

    public static List<Field> setAnnotationedFields(Class clazz) {
        List<Field> result = Lists.newArrayList();
        Field[] fields = clazz.getDeclaredFields();
        Class<?> pcl = clazz.getSuperclass();
        //递归获取父类所有的field
        while (!pcl.equals(Object.class)) {
            fields = (Field[]) ArrayUtils.addAll(fields, pcl.getDeclaredFields());
            pcl = pcl.getSuperclass();
        }
        for (Field field : fields) {
            Required required = field.getDeclaredAnnotation(Required.class);
            if (required == null) {
                continue;
            }
            result.add(field);
        }
        cache.put(clazz.getName(), result);
        return result;
    }
}
