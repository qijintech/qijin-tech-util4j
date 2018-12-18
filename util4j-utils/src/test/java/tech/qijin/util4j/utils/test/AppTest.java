package tech.qijin.util4j.utils.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import tech.qijin.util4j.utils.ResBuilder;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void builderTest() {
        System.out.println(JSON.toJSONString(ResBuilder.genError(1, "heh")));
    }
}
