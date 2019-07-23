package tech.qijin.util4j.utils.test;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.utils.DateUtil;
import tech.qijin.util4j.utils.TimezoneUtil;

/**
 * @author michealyang
 * @date 2019/2/20
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class TimezoneUtilTest {
    @Test
    public void dayOfWeek() throws ParseException {
        String str = "2019-02-01";
        DateTime dateTime = TimezoneUtil.parse(str, "UTC-8");
        int res = TimezoneUtil.dayOfWeek(dateTime);
        log.info("res={}", res);

        DateTime first = TimezoneUtil.firstDayOfWeek(dateTime, res);
        DateTime last = TimezoneUtil.lastDayOfWeek(dateTime, res);
        log.info("res={}", first);
        log.info("res={}", last);
    }

    @Test
    public void daysInterval() {
        String str = "2019-02-01";
        DateTime dateTime1 = TimezoneUtil.parse(str, "UTC-8");
        str = "2019-02-09";
        DateTime dateTime2 = TimezoneUtil.parse(str, "UTC-8");
        int res = TimezoneUtil.daysInterval(dateTime1, dateTime2);
        log.info("res={}", res);
        log.info("res={}", dateTime2.minusDays(7));
    }

    @Test
    public void dayOfMonth() {
        String str = "2019-03-12";
        DateTime dateTime = TimezoneUtil.parse(str, "UTC-8");
        DateTime first = TimezoneUtil.firstDayOfMonth(dateTime);
        DateTime last = TimezoneUtil.lastDayOfMonth(dateTime);
        log.info("first={}", first);
        log.info("last={}", last);
    }

    @Test
    public void getWeekOfYear() {
        Date now = DateUtil.now();
        log.info("res={}", TimezoneUtil.weekOfYear(now, "UTC+8"));
        String str = "2019-02-20";
        DateTime dateTime = TimezoneUtil.parse(str, "UTC-8");
        int res = TimezoneUtil.weekOfYear(dateTime);
        log.info("res={}", res);
    }

    @Test
    public void test() {
        Integer test = null;
        set(test);
        log.info("test={}", test);
    }

    public void set(Integer test) {
        test = 1;
    }
}
