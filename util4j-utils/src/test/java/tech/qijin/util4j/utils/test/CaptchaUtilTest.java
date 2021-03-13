package tech.qijin.util4j.utils.test;

import org.junit.Test;
import tech.qijin.util4j.utils.CaptchaUtil;

public class CaptchaUtilTest {
    @Test
    public void genNumberCodeTest() {
        System.out.println(CaptchaUtil.genNumberCode(6));
    }
}
