package tech.qijin.util4j.rpc.test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author UnitTest
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplicationTest.class)
@ActiveProfiles(profiles = "dev")
public class BaseTest {
    protected static final Logger log = LoggerFactory.getLogger("TEST");

    @BeforeClass
    public static void beforeClass() {
    }
}
