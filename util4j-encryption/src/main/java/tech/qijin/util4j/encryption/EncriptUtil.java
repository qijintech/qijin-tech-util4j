package tech.qijin.util4j.encryption;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author michealyang
 * @date 2018/11/30
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class EncriptUtil {
    /**
     * base58编码
     *
     * @return
     */
    public static String base58Encode(String value) {
        return InnerClass.base58().encode(value);
    }

    public static String base58Encode(Long value) {
        return InnerClass.base58().encode(value);
    }

    /**
     * md5编码，默认返回hex字符串
     *
     * @param value
     * @return
     */
    public static String md5Encode(String value) {
        return DigestUtils.md5Hex(value);
    }

    static class InnerClass {
        private static Base58 base58 = new Base58();

        public static Base58 base58() {
            return base58;
        }
    }
}
