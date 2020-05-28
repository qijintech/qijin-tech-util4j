package tech.qijin.util4j.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtil {

    private static final String TIME_FORMAT = "%02d";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String HHMMSS = "HH:mm:ss";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHH00 = "yyyyMMddHH00";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_SLASH = "yyyy/MM/dd";
    public static final String YYYYMMDD_ZH = "yyyy年MM月dd日";
    public static final String MINITES_ZH = "yyyy年MM月dd日 HH:mm分";
    public static final int FIRST_HOUR_OF_DAY = 0;

    /**
     * 每分钟的秒数
     */
    public static final int SECONDS_PER_MINUTE = 60;
    /**
     * 每小时的分钟数
     */
    public static final int MINUTES_PER_HOUR = 60;
    /**
     * 每小时的秒数
     */
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    /**
     * 每天小时数
     */
    public static final int HOURS_PER_DAY = 24;
    /**
     * 每天分钟数
     */
    public static final int MINUTES_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR;
    /**
     * 每天秒数
     */
    public static final int SECONDS_PER_DAY = HOURS_PER_DAY * SECONDS_PER_HOUR;
    /**
     * 每秒的毫秒数
     */
    public static final int MILLI_PER_SECOND = 1000;


    /**
     * 返回unix时间戳
     *
     * @return
     */
    public static long unix() {
        return now().getTime() / MILLI_PER_SECOND;
    }

    /**
     * 返回unix时间戳毫秒精度
     *
     * @return
     */
    public static long unixMilli() {
        return now().getTime();
    }

    /**
     * 返回当前日期
     *
     * @return Date
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 将秒数变成毫秒数
     *
     * @param seconds
     * @return
     */
    public static long seconds2mill(long seconds) {
        return MILLI_PER_SECOND * seconds;
    }

    /**
     * 与{@link #seconds2mill(long)} 相同
     *
     * @param seconds
     * @return
     */
    public static long s2m(long seconds) {
        return seconds2mill(seconds);
    }

    /**
     * 得到这一天的起始, 00:00:00
     *
     * @param date
     * @return yyyy-MM-dd 00:00:00
     */
    public static Date getDayBegin(Date date) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 得到这一天的结束 23:59:59
     *
     * @param date
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date getDayEnd(Date date) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(0).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 在当前时间基础上增减n天
     *
     * @param date
     * @param n    为正时表示往后推n天，为负时表示往前推n天
     * @return
     */
    public static Date incrDays(Date date, int n) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .plusDays(n).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 在当前时间基础上增减n小时
     *
     * @param date
     * @param n
     * @return
     */
    public static Date incrHours(Date date, int n) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .plusHours(n).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 在当前时间基础上增减n分钟
     *
     * @param date
     * @param n
     * @return
     */
    public static Date incrMinutes(Date date, int n) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                .plusMinutes(n).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param date
     * @param day     day = -1 表示往前推1天 0为当天
     * @param hour
     * @param minute
     * @param seconds
     * @return
     */
    public static Date getDate(Date date, int day, int hour, int minute, int seconds) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(day).withHour(hour)
                .withMinute(minute).withSecond(seconds).withNano(0).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getTodayZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    public static Date offsetDays(Date date, int offsetDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, offsetDays);
        return calendar.getTime();//获取一年前的时间，或者一个月前的时间
    }


    public static String nowInMinitesZH() {
        return DateTimeFormatter.ofPattern(MINITES_ZH).format(LocalDateTime.now());
    }

    public static Date today() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String format(String date, String fromFormat, String toFormat) {
        return DateTime.parse(date, DateTimeFormat.forPattern(fromFormat)).toString(toFormat);
    }


    public static String formatStr(long date, String fromFormat) {
        return new DateTime(date).toString(fromFormat);
    }

    public static String formatStr(Date date, String fromFormat) {
        if (date == null) {
            throw new NullPointerException("Date can not be null !");
        }
        return formatStr(date.getTime(), fromFormat);
    }

    public static String formatZH(long date) {
        return formatStr(date, YYYYMMDD_ZH);
    }

    /**
     * 得到每月开始第一天，0时0分0秒
     *
     * @param addMonth 0表示当月 -1前一个月 -2大上个月
     * @return yyyy-MM-dd 00:00:00
     */
    public static Date getMonthBegin(int addMonth) {
        return Date.from(LocalDate.now().plusMonths(addMonth).with(TemporalAdjusters.firstDayOfMonth())
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 得到每月最后一天，23:59:59
     *
     * @param addMonth 0表示当月 -1前一个月 -2大上个月
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date getMonthEnd(int addMonth) {
        return Date.from(LocalDateTime.now().plusMonths(addMonth).with(TemporalAdjusters.lastDayOfMonth()).withHour(23)
                .withMinute(59).withSecond(59).withNano(0).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 得到前一天，23:59:59
     *
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date getLastDay() {
        return getLastDay(new Date());
    }

    /**
     * 得到前一天，23:59:59
     *
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date getLastDay(Date date) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).minusDays(1).withHour(23)
                .withMinute(59).withSecond(59).withNano(0).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取date前第N天日期 包括date
     *
     * @return yyyy-MM-dd 00:00:00
     */
    public static Date getDataBeforeDays(Date date, int days) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0)
                .withSecond(0).withNano(0).minusDays(days - 1L).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取date后第days天的结束日期
     *
     * @return yyyy-MM-dd 23:59:59
     */
    public static Date getEndDateAfterDays(Date date, int days) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59)
                .withSecond(59).withNano(0).plusDays(days).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取date后第days天的开始日期
     *
     * @return yyyy-MM-dd 00:00:00
     */
    public static Date getStartDateAfterDays(Date date, int days) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0)
                .withSecond(0).withNano(0).plusDays(days).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 确认该日期是不是当前天
     *
     * @param date
     * @return boolean
     */
    public static boolean isToday(Date date) {
        return LocalDate.now().equals(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * 确认两个日期是不是同一天
     *
     * @param date
     * @param compareDate
     * @return
     */
    public static boolean isSameDay(Date date, Date compareDate) {
        // 后面的日期必须大于前面日期
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate()
                .equals(LocalDateTime.ofInstant(compareDate.toInstant(), ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * 得到输入日期为星期几(ch)
     *
     * @param date
     * @return
     */
    public static String getWeekDayChinese(Date date) {
        return DateTimeFormatter.ofPattern("E", Locale.SIMPLIFIED_CHINESE)
                .format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getDayOfWeek());
    }

    /**
     * @return yyyyMMdd
     */
    public static String getDateFormatStr(Date date) {
        return DateTimeFormatter.ofPattern(YYYYMMDD)
                .format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * @param dateStr yyyyMMdd
     */
    public static Date getDateFormat(String dateStr) {
        return Date.from(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(YYYYMMDD))
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /**
     * @param dateStr yyyyMMdd
     */
    public static Date getDateFormat(String dateStr, String formatStr) {
        return Date.from(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(formatStr))
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 两个日期相差天数
     */
    public static Long getDimDays(Date day1, Date day2) {
        LocalDateTime localTime1 = LocalDateTime.ofInstant(day1.toInstant(), ZoneId.systemDefault()).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime localTime2 = LocalDateTime.ofInstant(day2.toInstant(), ZoneId.systemDefault()).withMinute(0)
                .withSecond(0).withNano(0);
        return Math.abs(Duration.between(localTime1, localTime2).getSeconds() / (60 * 60 * 24));
    }

    /**
     * 两个日期相减-差值
     *
     * @param day1
     * @param day2
     * @return
     */
    public static Long getMinusMinutes(Date day1, Date day2) {
        long l1 = day1.getTime();
        long l2 = day2.getTime();
        return (l1 - l2) / (1000 * 60);
    }

    /**
     * 两个日期相减－差值－秒
     *
     * @param day1
     * @param day2
     * @return
     */
    public static Long getMinusSeconds(Date day1, Date day2) {
        long l1 = day1.getTime();
        long l2 = day2.getTime();
        return (l1 - l2) / 1000;
    }

    /**
     * 仅使用HHmmss比较两个date
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDateUseTimeOnly(Date date1, Date date2) {
        LocalTime time1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault()).toLocalTime();
        LocalTime time2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault()).toLocalTime();
        return time1.compareTo(time2);
    }

    /**
     * 获取当前的周数
     */
    public static int getCurrentWeek() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前周几
     */
    public static int getCurrentWeekDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getCurrentHourOfDay() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @return yyyyMMdd
     */
    public static String getCurrentDay() {
        return DateTimeFormatter.ofPattern(YYYYMMDD).format(LocalDate.now());
    }

    /**
     * 1970-01-01 00:00:00
     *
     * @return
     */
    public static Date getDbDefaultDate() {
        return Date
                .from(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 输入date是否合法
     *
     * @param date
     * @return
     */
    public static boolean isValidDate(Date date) {
        return date != null && date.after(getDbDefaultDate());
    }


    /**
     * @param date
     * @param month 大于0 则是month个月后 小于0 month个月前
     * @return
     */
    public static Date getTheCorrespondingPeriodDay(Date date, int month) {
        return Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusMonths(month)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将输入date转换为seconds
     *
     * @param date
     * @return
     */
    public static int transToSeconds(Date date) {
        return (int) TimeUnit.MILLISECONDS.toSeconds(date.getTime());
    }


    /**
     * 返回开始日期和结束日期之间的全部日期（包括开始和结束日期）
     *
     * @param startDate
     * @param endDate
     * @return yyyy-MM-dd 00:00:00
     */
    public static List<Date> getAllDaysBetween(Date startDate, Date endDate) {

        List<Date> result = new ArrayList<Date>();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(getDayBegin(startDate));

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(getDayBegin(endDate));

        Calendar temp = startCalendar;
        while (!temp.after(endCalendar)) {
            result.add(temp.getTime());
            temp.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 是否是数据库的默认时间
     *
     * @param date
     * @return
     */
    public static boolean isDbDefaultDate(Date date) {
        return date.compareTo(getDbDefaultDate()) == 0;
    }


    public static void main(String[] args) {
        Date now = now();
        System.out.println(now);
        Date a = incrDays(now, 1);
        System.out.println(a);
        Date b = getDate(now, 1, 0, 0, 0);
        System.out.println(b);
    }
}
