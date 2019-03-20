package tech.qijin.util4j.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author michealyang
 * @date 2019/3/16
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class FileUtilTest {
    @Test
    public void test() throws FileNotFoundException {
        File file = ResourceUtils.getFile("config/test.txt");
        log.info("res={}", file.getAbsolutePath());
    }
}
