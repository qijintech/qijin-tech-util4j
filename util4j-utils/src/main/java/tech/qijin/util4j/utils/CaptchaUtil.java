package tech.qijin.util4j.utils;

import java.util.Random;

/**
 * 验证码生成工具，包括手机验证码和图片验证码
 */
public class CaptchaUtil {
    /**
     * 生成手机验证码
     * @param n 验证码长度，如4、6、8
     * @return
     */
    public static String genNumberCode(int n) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (n-- > 0) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
