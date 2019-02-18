package tech.qijin.util4j.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author michealyang
 * @date 2019/2/18
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 * @refer http://publib.boulder.ibm.com/tividd/td/TWS/SH19-4555-00/zh_CN/HTML/ig_mst111.htm
 * @refer https://garygregory.wordpress.com/2013/06/18/what-are-the-java-timezone-ids/
 **/
public class TimezoneUtil {

    private static String pattern = "UTC[+-]{1}([0-9]|1[0-1]){1}";
    private static Pattern p = Pattern.compile(pattern);


    /**
     * 将指定date调整成指定timezone的DateTime格式
     *
     * @param date
     * @param timezone * 格式为: UTC[+/-][n]
     *                 * 东n区 -> UTC+n，如UTC+8表示东8区
     *                 * 西n区 -> UTC-n，如UTC-8表示西8区
     *                 * 0时区使用UTC-0或者UTC+0均可
     * @return
     */
    public static DateTime coordinate(Date date, String timezone) {
        TimeZone timeZone = getTimeZone(timezone);
        return new DateTime(DateTimeZone.forTimeZone(timeZone));
    }

    /**
     * 将指定date调整成指定timezone的字符串格式
     *
     * @param date
     * @param timezone * 格式为: UTC[+/-][n]
     *                 * 东n区 -> UTC+n，如UTC+8表示东8区
     *                 * 西n区 -> UTC-n，如UTC-8表示西8区
     *                 * 0时区使用UTC-0或者UTC+0均可
     * @return
     */
    public static String toString(Date date, String timezone, String format) {
        return coordinate(date, timezone).toString(format);
    }

    /**
     * 获取指定date在指定timezone下的年份
     *
     * @param date
     * @param timezone
     * @return
     */
    public static int getYear(Date date, String timezone) {
        return coordinate(date, timezone).getYear();
    }

    /**
     * 获取指定date在指定timezone下的月份
     *
     * @param date
     * @param timezone
     * @return
     */
    public static int getMonth(Date date, String timezone) {
        return coordinate(date, timezone).getMonthOfYear();
    }

    /**
     * 获取指定date在指定timezone下的天
     *
     * @param date
     * @param timezone
     * @return
     */
    public static int getDay(Date date, String timezone) {
        return coordinate(date, timezone).getDayOfMonth();
    }

    public static int getHour(Date date, String timezone) {
        return coordinate(date, timezone).getHourOfDay();
    }

    public static int getMinute(Date date, String timezone) {
        return coordinate(date, timezone).getMinuteOfHour();
    }

    public static int getSecond(Date date, String timezone) {
        return coordinate(date, timezone).getSecondOfMinute();
    }

    /**
     * 计算指定日期在一年当中是第几周
     *
     * @param date
     * @param timezone
     * @return
     */
    public static int getWeekOfYear(Date date, String timezone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTimeZone(getTimeZone(timezone));
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 校验timezone的格式是否正确
     *
     * @param timezone
     * @return
     */
    public static boolean validateTimezone(String timezone) {
        if (StringUtils.isBlank(timezone)) {
            return false;
        }
        if (!p.matcher(timezone).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 校验timezone的格式合法性
     *
     * @param timezone
     * @return
     */
    private static void checkTimezone(String timezone) {
        if (StringUtils.isBlank(timezone)) {
            throw new IllegalStateException("timezone should not be blank");
        }
        if (!p.matcher(timezone).matches()) {
            throw new IllegalStateException("invalid timezone format");
        }
    }

    private static TimeZone getTimeZone(String utc) {
        checkTimezone(utc);
        String gmt = utc2gmt(utc);
        return TimeZone.getTimeZone(gmt);
    }

    public static String utc2gmt(String utc) {
        checkTimezone(utc);
        return utc.replace("UTC", "GMT");
    }
}
