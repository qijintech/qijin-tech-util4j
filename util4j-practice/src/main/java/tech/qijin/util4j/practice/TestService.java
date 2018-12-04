package tech.qijin.util4j.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author michealyang
 * @date 2018/11/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Service
public class TestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

    public void sayHello(String content) {
        LOGGER.info("msg={}, at={}", content, new Date());
    }
}
