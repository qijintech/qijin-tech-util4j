package tech.qijin.util4j.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tech.qijin.util4j.utils.IpUtil;

/**
 * @author michealyang
 * @date 2019/2/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class IpUtilTest {
    @Test
    public void test() {
        log.info("res={}", IpUtil.inRange("10.153.48.127", "10.153.48.0/26"));
        log.info("res={}", IpUtil.inRange("10.168.1.2", "10.168.0.224/23"));
        log.info("res={}", IpUtil.inRange("192.168.0.1", "192.168.0.0/24"));
        log.info("res={}", IpUtil.inRange("10.168.0.0", "10.168.0.0/32"));
    }
}
