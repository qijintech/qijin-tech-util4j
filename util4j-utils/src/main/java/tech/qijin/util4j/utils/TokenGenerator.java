package tech.qijin.util4j.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * @author michealyang
 * @date 2018/10/31
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class TokenGenerator {

    /**
     * use UUID as token
     * @return
     */
    public static String gen() {
        return UUID.randomUUID().toString();
    }

    public static String md5Gen(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        long now = System.currentTimeMillis() / 1000;
        String v = now + "-" + value;
        return DigestUtils.md5DigestAsHex(v.getBytes());
    }


    public static  String md5GenByUserId(Long userId){
        return md5Gen(String.valueOf(userId));
    }
}
