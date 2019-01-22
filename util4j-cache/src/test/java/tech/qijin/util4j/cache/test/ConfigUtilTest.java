package tech.qijin.util4j.cache.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tech.qijin.util4j.cache.util.ConfigUtil;

/**
 * @author michealyang
 * @date 2019/1/21
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class ConfigUtilTest {

    @Test
    public void load() {
        log.info(ConfigUtil.get("test", "hehe2"));
    }
}
