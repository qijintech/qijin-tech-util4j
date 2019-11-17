package tech.qijin.util4j.web.common;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019-11-07
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
public class Constants {
    public static final String FORMAT_ANY = "ANY";
    public static final String FORMAT_DAY = "YYYY-MM-DD";
    public static final String FORMAT_HOUR = "YYYY-MM-DD HH";
    public static final String FORMAT_MINUTE = "YYYY-MM-DD HH:mm";
    public static final String FORMAT_SECOND = "YYYY-MM-DD HH:mm:ss";
    public static final String DATE_FORMAT = "YYYY-MM-DD HH:mm:ss";

    public static final String REGEX_DAY = "\\d{4}-\\d{2}-\\d{2}";
    public static final String REGEX_HOUR = "\\d{4}-\\d{2}-\\d{2} \\d{2}";
    public static final String REGEX_MINUTE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    public static final String REGEX_SECOND = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

    public static final Pattern PATTERN_DAY = Pattern.compile(REGEX_DAY);
    public static final Pattern PATTERN_HOUR = Pattern.compile(REGEX_HOUR);
    public static final Pattern PATTERN_MINUTE = Pattern.compile(REGEX_MINUTE);
    public static final Pattern PATTERN_SECOND = Pattern.compile(REGEX_SECOND);

    public static final Map<String, Pattern> FORMAT_PATTERN_MAP = Maps.newHashMap();

    static {
        FORMAT_PATTERN_MAP.put(FORMAT_DAY, PATTERN_DAY);
        FORMAT_PATTERN_MAP.put(FORMAT_HOUR, PATTERN_HOUR);
        FORMAT_PATTERN_MAP.put(FORMAT_MINUTE, PATTERN_MINUTE);
        FORMAT_PATTERN_MAP.put(FORMAT_SECOND, PATTERN_SECOND);
    }

    /**
     * 密码强度
     */
    public enum PWDStrenth {
        // 任意
        ANY,
        // 必须包含数字，字母
        MIDDLE,
        // 必须包含数字，小写字母，大写字母
        HIGH,
        // 必须包含数字，小写字母，大写字母，特殊字符等
        STRONG
    }
}
