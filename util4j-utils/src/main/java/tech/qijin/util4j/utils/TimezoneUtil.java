package tech.qijin.util4j.utils;

import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    private final static String OFFSET_SEPARATOR = "UTC";
    private static int DAYS_OF_WEEK = 7;

    /**
     * 将指定date调整成指定timezone的DateTime格式
     *
     * @param day      必须符合yyyy-MM-dd格式，如2019-01-01
     * @param timezone * 格式为: UTC[+/-][n]
     *                 * 东n区 -> UTC+n，如UTC+8表示东8区
     *                 * 西n区 -> UTC-n，如UTC-8表示西8区
     *                 * 0时区使用UTC-0或者UTC+0均可
     * @return
     */
    public static DateTime parse(String day, String timezone) {
        return DateTime.parse(getParseFormat(day, timezone));
    }

    private static String getOffset(String timezone) {
        String[] arr = timezone.split(OFFSET_SEPARATOR);
        return arr[1];
    }

    private static String getParseFormat(String day, String timezone) {
        String offset = getOffset(timezone);
        if (offset.length() < 3) {
            offset = new StringBuilder().append(offset.substring(0, 1))
                    .append("0")
                    .append(offset.substring(1, offset.length()))
                    .toString();
        }
        return new StringBuilder()
                .append(day)
                .append("T")
                .append(offset)
                .append(":00")
                .toString();
    }

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
    public static int weekOfYear(Date date, String timezone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTimeZone(getTimeZone(timezone));
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 需要DateTime包含正确的时区
     *
     * @param dateTime
     * @return
     */
    public static int weekOfYear(DateTime dateTime) {
        return dateTime.get(DateTimeFieldType.weekOfWeekyear());
    }

    /**
     * 返回该天是一周的第几天，sunday作为第一天
     * 需要DateTime包含正确的时区
     *
     * @param dateTime
     * @return
     */
    public static int dayOfWeek(DateTime dateTime) {
        Calendar calendar = dateTime.toCalendar(Locale.US);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得指定date所在的week
     *
     * @param dateTime
     * @return
     */
    public static Pair<DateTime, DateTime> weekOfDay(DateTime dateTime) {
        int pos = dayOfWeek(dateTime);
        DateTime firstDay = firstDayOfWeek(dateTime, pos);
        DateTime lastDay = lastDayOfWeek(dateTime, pos);
        return new Pair<>(firstDay, lastDay);
    }

    /**
     * 获得指定date所在week的第一天
     *
     * @param dateTime
     * @return
     */
    public static DateTime firstDayOfWeek(DateTime dateTime) {
        int pos = dayOfWeek(dateTime);
        return dateTime.minusDays(pos - 1);
    }

    public static DateTime firstDayOfWeek(DateTime dateTime, int pos) {
        return dateTime.minusDays(pos - 1);
    }

    /**
     * 获得指定date所在week的最后一天
     *
     * @param dateTime
     * @return
     */
    public static DateTime lastDayOfWeek(DateTime dateTime) {
        int pos = dayOfWeek(dateTime);
        return dateTime.plusDays(DAYS_OF_WEEK - pos);
    }

    public static DateTime lastDayOfWeek(DateTime dateTime, int pos) {
        return dateTime.plusDays(DAYS_OF_WEEK - pos);
    }

    /**
     * 获得一个月份的第一天
     *
     * @param dateTime
     * @return
     */
    public static DateTime firstDayOfMonth(DateTime dateTime) {
        Calendar calendar = dateTime.toCalendar(Locale.US);
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        return new DateTime(calendar.getTime(), dateTime.getZone());
    }

    /**
     * 获得一个月份的最后一天
     *
     * @param dateTime
     * @return
     */

    public static DateTime lastDayOfMonth(DateTime dateTime) {
        Calendar calendar = dateTime.toCalendar(Locale.US);
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return new DateTime(calendar.getTime(), dateTime.getZone());
    }

    public static int daysInterval(DateTime dateTime1, DateTime dateTime2) {
        return Math.abs(dateTime2.getDayOfYear() - dateTime1.getDayOfYear());
    }

    /**
     * 判断一个date是否在一个week内
     *
     * @param dateTime
     * @param week
     * @return
     */
    public static boolean dayInWeek(DateTime dateTime, Pair<DateTime, DateTime> week) {
        return compare(week.getValue(), dateTime) >= 0
                && compare(dateTime, week.getKey()) >= 0;
    }

    /**
     * 比较两个时间的大小
     *
     * @param dateTime1
     * @param dateTime2
     * @return <0 if (dateTime1 < dateTime2)
     * =0 if (dateTime1 = dateTime2)
     * >0 if (dateTime1 > dateTime2)
     */
    public static long compare(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getMillis() - dateTime2.getMillis();
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
