package tech.qijin.util4j.lang.constant;

/**
 * @author michealyang
 * @date 2019/3/18
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class Const {
    public static final String ENV_VIRTUAL_HOST = "virtual.";

    /**
     * 100%=10000/10000
     */
    public static final int PERCENT_BASE = 10000;

    /**
     * 当概率的分母为0时，概率取最大值
     */
    public static final long MAX_PERCENT = 0xFFFFFFFF;

    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PROD = "prod";
    public static final String STRESS = "stress";
    public static final String STAGING = "staging";

    /**
     * timestamp能表示的最大时间：2018-01-19。
     */
    public static final Long MAX_TIMESTAMP = 2147443200000L;

    /**
     * 日期格式
     */
    public static final String DATE_FORMAT_DASHED_D = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DASHED_H = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_DASHED_M = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_DASHED_S = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyyMMdd";

    /**
     * 文件大小
     */
    public static final long KB = 1 << 10;
    public static final long MB = KB << 10;
    public static final long GB = MB << 10;
    public static final long TB = GB << 10;

    /**
     * 文件后缀类型
     */
    // 图片
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String BMP = "bmp";
    public static final String PNG = "png";
    public static final String GIF = "gif";
}
