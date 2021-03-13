package tech.qijin.util4j.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tech.qijin.util4j.utils.Util;

/**
 * @author michealyang
 * @date 2019-12-19
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Slf4j
public class UtilTest {
    @Test
    public void testRunIgnoreEx() {
        int a = 10;
        log.info("asdfasdf");
    }

    private boolean test(int a) {
        if (true) {
            throw new IllegalStateException("asdf");
        }
        log.info("a={}", a);
        return false;
    }
}
