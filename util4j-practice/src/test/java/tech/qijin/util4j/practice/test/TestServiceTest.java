package tech.qijin.util4j.practice.test;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.practice.service.TestService2;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/3/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class TestServiceTest extends BaseTest {
    @Autowired
    private TestService2 testService;

    @Test
    public void test() {
        List<Long> ids = Lists.newArrayList();
        ids.add(1L);
        log.info("res={}", testService.test(ids));
    }

    @Test
    public void test2() {
        testService.test2();
    }
}
