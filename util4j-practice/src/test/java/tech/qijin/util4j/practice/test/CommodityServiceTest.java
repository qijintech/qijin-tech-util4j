package tech.qijin.util4j.practice.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.practice.model.Commodity;
import tech.qijin.util4j.practice.model.User;
import tech.qijin.util4j.practice.service.CommodityService;
import tech.qijin.util4j.practice.service.UserService;

/**
 * @author michealyang
 * @date 2018/12/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class CommodityServiceTest extends TestServerApplicationTests {
    @Autowired
    private CommodityService commodityService;

    @Test
    public void insert() {
        Commodity commodity = new Commodity();
        commodity.setTitle("title");
        commodity.setPrice(10);
        commodity.setQuatity(10);
        commodity.setPic("pics");
        commodity.setSlide("slide");
        Commodity res = commodityService.insert(commodity);
        log.info(res.toString());
    }
}
