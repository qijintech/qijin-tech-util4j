package tech.qijin.util4j.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tech.qijin.util4j.utils.AsyncUtil;

/**
 * @author michealyang
 * @date 2019-12-19
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Slf4j
public class AsyncUtilTest {
    @Test
    public void testSubmit() {
        for (int i = 0; i < 100; i++) {
            AsyncUtil.submit(() -> test());
        }
    }

    public boolean test() {
        log.info("value={}", System.nanoTime());
        return true;
    }
}
