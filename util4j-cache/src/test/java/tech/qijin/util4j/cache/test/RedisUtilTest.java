package tech.qijin.util4j.cache.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tech.qijin.util4j.cache.redis.RedisUtil;

/**
 * @author michealyang
 * @date 2019/1/21
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class RedisUtilTest {
    @Test
    public void set() {
        boolean res = RedisUtil.set("key", "value");
        log.info("res={}", res);
    }

    @Test
    public void get() {
        log.info("res={}", RedisUtil.get("key"));
    }

    @Test
    public void setObject() {
        AClass aClass = new AClass();
        aClass.setA("a");
        aClass.setB(2);
        aClass.setC(true);
        aClass.setBClass(new BClass());

        boolean res = RedisUtil.setObject("object", aClass);
        log.info("res={}", res);
    }


    @Test
    public void getObject() {
        AClass aClass = (AClass)RedisUtil.getObject("object");
        log.info("res={}", aClass);
        log.info("bClass={}", aClass.getBClass());
    }

    @Test
    public void del() {
        Long res = RedisUtil.del("object");
        log.info("res={}", res);
    }
}
