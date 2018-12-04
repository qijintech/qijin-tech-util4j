package tech.qijin.util4j.utils.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public class MD5Test {

    private static final Logger LOGGER =LoggerFactory.getLogger(MD5Test.class);

    @Test
    public void testMD5() {
        String originValue = "dadadasda";
        String digestValue = DigestUtils.md5DigestAsHex(originValue.getBytes());
        LOGGER.info("origin value = {} digest value = {}",originValue,digestValue );
    }
}
