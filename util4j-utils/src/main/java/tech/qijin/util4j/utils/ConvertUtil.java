package tech.qijin.util4j.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.utils.convert.BeanWrapper;

@Slf4j
public class ConvertUtil {

    private static final Map<String, Map<String, Field>> fieldsMap = new ConcurrentHashMap<>(128);

    private ConvertUtil() {
    }

    public static <A, B> List<B> convertList(List<A> from, Class<B> to) {
        List<B> creDtos = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(from)) {
            for (A a : from) {
                creDtos.add(convert(a, to));
            }
        }
        return creDtos;
    }

    /**
     * 用于各种vo、domain之间的转换 用法：vo、domain之间的属性取名保持相同
     *
     * @param from
     * @param to
     * @return
     */
    public static <A, B> B convert(A from, Class<B> to) {
        if (from == null) {
            return null;
        }
        try {
            Map<String, Field> toMap = getFields(to);
            BeanWrapper<B> beanWrapper = new BeanWrapper<>(to, toMap);
            Map<String, Field> fromMap = getFields(from.getClass());

            for (Map.Entry<String, Field> entry : toMap.entrySet()) {
                Field fromField = fromMap.get(entry.getKey());
                if (fromField != null && !entry.getValue().isSynthetic()) {
                    Object value = getValue(from, entry.getKey());
                    beanWrapper.setPropertyValue(entry.getKey(), value, fromField);
                }
            }
            return beanWrapper.getObject();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            String errMsg = "转换失败";
            log.error(errMsg, e);
            throw new RuntimeException(errMsg);
        }
    }

    private static <A> Object getValue(A from, String key) throws IllegalAccessException, InvocationTargetException {
        Object value = null;
        try {
            value = PropertyUtils.getProperty(from, key);
        } catch (NoSuchMethodException e) {
        }
        return value;
    }

    private static Map<String, Field> getFields(Class<?> clazz) {
        if (fieldsMap.containsKey(clazz.getName())) {
            return fieldsMap.get(clazz.getName());
        }
        Map<String, Field> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        Class<?> pcl = clazz.getSuperclass();
        while (!pcl.equals(Object.class)) {
            fields = (Field[]) ArrayUtils.addAll(fields, pcl.getDeclaredFields());
            pcl = pcl.getSuperclass();
        }
        for (Field field : fields) {
            map.put(field.getName(), field);
        }
        fieldsMap.put(clazz.getName(), map);
        return map;
    }
}
