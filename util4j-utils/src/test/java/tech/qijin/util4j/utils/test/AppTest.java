package tech.qijin.util4j.utils.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tech.qijin.util4j.utils.FileUtil;
import tech.qijin.util4j.utils.ResBuilder;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    @Test
    public void builderTest() {
        System.out.println(JSON.toJSONString(ResBuilder.genError(1, "heh")));
    }

    @Test
    public void test() throws IOException, URISyntaxException {
        BigDecimal bigDecimal = new BigDecimal("10.12").multiply(new BigDecimal("100"));
        log.info("res={}", bigDecimal.longValueExact());
    }
}
