package tech.qijin.util4j.lang.template;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成缓存key的模板
 *
 * @author michealyang
 * @date 2018/12/19
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface ICacheKey {

    String KEY_SEPARATOR = ".";

    /**
     * 返回对应module的名字
     *
     * @return
     */
    String module();

    default String key(String... keys) {
        StringBuilder sb = new StringBuilder();
        sb.append(module())
                .append(KEY_SEPARATOR)
                .append(StringUtils.join(keys, KEY_SEPARATOR));
        return sb.toString();
    }
}
