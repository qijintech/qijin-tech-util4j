package tech.qijin.util4j.lang.constant;

public interface EnumValue<T> {
    /**
     * 对应数据库的值
     * @return
     */
    T value();

    /**
     * 描述
     * @return
     */
    String desc();
}
