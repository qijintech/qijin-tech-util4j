package tech.qijin.util4j.cache.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author michealyang
 * @date 2019/1/21
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class ConfigUtil {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            InputStream inputStream = ConfigUtil.class.getClassLoader()
                    .getResourceAsStream("cache.properties");
            if (inputStream == null) {
                log.error("can not find file cache.properties error");
            } else {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            log.error("load cache.properties error", e);
        }
    }

    public static String get(String key, String defaultValue) {
        String value = (String) properties.get(key);
        return StringUtils.isNotBlank(value) ? value : defaultValue;
    }

    public static boolean get(String key) {
        String value = (String) properties.get(key);
        return "true".equalsIgnoreCase(value);
    }
}
