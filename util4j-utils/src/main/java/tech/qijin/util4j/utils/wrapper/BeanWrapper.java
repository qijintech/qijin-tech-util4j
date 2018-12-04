package tech.qijin.util4j.utils.wrapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;

public class BeanWrapper<T> {
    private T object;
    private Map<String, Field> fieldMap = null;

    public BeanWrapper(Class<T> clazz, Map<String, Field> fieldMap)
            throws InstantiationException, IllegalAccessException {
        this.object = clazz.newInstance();
        this.fieldMap = fieldMap;
    }

    public void setPropertyValue(String property, Object value, Field fromType)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Field toField = fieldMap.get(property);
        if (!toField.isSynthetic() && value != null) {
            if (fromType.getType().equals(toField.getType()) || isConvertible(fromType.getType(), toField.getType())) {
                PropertyUtils.setProperty(object, property, value);
            } else if (BigDecimal.class.isInstance(value) && toField.getType().equals(String.class)) {
                // 从BigDecimal 转成String，特殊处理
                PropertyUtils.setProperty(object, property, value.toString());
            } else if (String.class.isInstance(value) && toField.getType().equals(BigDecimal.class)) {
                PropertyUtils.setProperty(object, property, new BigDecimal((String) value));
            }
        }

    }

    public T getObject() {
        return object;
    }

    boolean isConvertible(Class<?> classA, Class<?> classB) {
        if (ClassUtils.isPrimitiveOrWrapper(classA) && ClassUtils.isPrimitiveOrWrapper(classB)) {
            return isPrimitiveAndWrapper(classA, classB);
        }
        return false;
    }

    boolean isPrimitiveAndWrapper(Class<?> classA, Class<?> classB) {
        if ((ClassUtils.isPrimitiveWrapper(classA) && ClassUtils.wrapperToPrimitive(classA).equals(classB))
                || (ClassUtils.isPrimitiveWrapper(classB) && ClassUtils.wrapperToPrimitive(classB).equals(classA))) {
            return true;
        }
        return false;
    }
}
