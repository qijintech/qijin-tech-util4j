package tech.qijin.util4j.timezone.test;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.timezone.TimezoneHandler;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/2/19
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class TimezoneHandlerTest extends BaseTest {

    @Autowired
    private TimezoneHandler timezoneHandler;

    @Test
    public void getPreviousNDays() {
        String endDay = "2019-02-15";
        List<DateTime> dateTimes = timezoneHandler.getPreviousNDays(endDay, 30);
        log.info("res={}", dateTimes);
    }

    @Test
    public void test() {
        String day = "2019-02-15T-08:00";
        DateTime dateTime = DateTime.parse(day);
        log.info("res={}", dateTime);
    }
}
