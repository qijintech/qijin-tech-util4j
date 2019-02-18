package tech.qijin.util4j.timezone.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019/2/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class TimezoneTest {

    @Test
    public void timezoneFormat() {
        String pattern = "UTC[+-]{1}([0-9]|1[0-1]){1}";
        Pattern r = Pattern.compile(pattern);
        String line = "UTC0";
        Assert.assertTrue(!r.matcher(line).matches());
        line = "UTC+8";
        Assert.assertTrue(r.matcher(line).matches());
        line = "UTC+12";
        Assert.assertTrue(!r.matcher(line).matches());
        line = "UTC-2";
        Assert.assertTrue(r.matcher(line).matches());
        line = "UTC-12";
        Assert.assertTrue(!r.matcher(line).matches());
        line = "UTC-9";
        Assert.assertTrue(r.matcher(line).matches());
        line = "UTC9";
        Assert.assertTrue(!r.matcher(line).matches());
        line = "UTC12";
        Assert.assertTrue(!r.matcher(line).matches());
        line = "UTC13";
        Assert.assertTrue(!r.matcher(line).matches());
        line = "UTC-11";
        Matcher matcher = r.matcher(line);
        if (matcher.find()) {
            log.info("yes");
            log.info("res={}", matcher.groupCount());
            log.info("res={}", matcher.group(0));
        }
    }

    @Test
    public void test() {
        Date date = new Date();
        log.info("res={}", date);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        log.info("res={}", calendar.get(Calendar.DAY_OF_MONTH));
        log.info("res={}", calendar.get(Calendar.HOUR_OF_DAY));
        log.info("res={}", calendar.get(Calendar.MINUTE));
        log.info("res={}", calendar.getTimeZone());
        log.info("res={}", calendar.get(Calendar.WEEK_OF_YEAR));

        log.info("=====");
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT-7"));
        DateTime dateTime = new DateTime(dateTimeZone);
        log.info("res={}", dateTime.toString("yyyyMMdd"));
        for(int i = 0; i<20; i++) {
            String timezone = "GMT-" + i + ":00";
            log.info("res={}", Calendar.getInstance(TimeZone.getTimeZone(timezone)).getTime());
            dateTime = new DateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezone)));
            log.info("dateTime={}", dateTime.toString());
            log.info("date={}", dateTime.toDate());
        }
    }


    public static String toTimezone(Date date, String timezone, String format) {
        String separator = "UTC";
        String[] arr = timezone.split(separator);
        int delta = Integer.valueOf(arr[1]);
        return new DateTime(date.getTime(), DateTimeZone.UTC)
                .plusHours(delta)
                .toString(format);
    }
}
