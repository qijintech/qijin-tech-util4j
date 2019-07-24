package tech.qijin.util4j.timezone;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.timezone.config.TimezoneProperties;
import tech.qijin.util4j.timezone.pojo.PageRes;
import tech.qijin.util4j.utils.TimezoneUtil;

/**
 * @author michealyang
 * @date 2019/2/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@Component
public class TimezoneHandler {
    private final String OFFSET_SEPARATOR = "UTC";
    private final int DAYS_OF_WEEK = 7;

    @Autowired
    private TimezoneProperties properties;

    public String getTimezone() {
        return properties.getValue();
    }

    public String getOffset() {
        String timezone = getTimezone();
        String[] arr = timezone.split(OFFSET_SEPARATOR);
        return arr[1];
    }

    /**
     * 将指定date转换成指定时区的Date。时区信息从配置文件中读取timezone.value变量
     *
     * @param date
     * @return
     */
    public DateTime coordinate(Date date) {
        return TimezoneUtil.coordinate(date, getTimezone());
    }

    /**
     * 将date转换成指定时区timezone的字符串形式
     * <p>timezone从配置文件中读取</p>
     * <p>
     * 如：北京时间Mon Feb 18 11:34:17 CST 2019，转换成UTC-8时区时，
     * 如果format=yyyyMMdd，则返回结果为20190217
     * </p>
     *
     * @param date
     * @param format yyyyMMdd或yyyy-MM-dd HH:mm:ss
     * @return
     */

    public String toString(Date date, String format) {
        return TimezoneUtil.toString(date, getTimezone(), format);
    }


    /**
     * 获取指定date在指定timezone下的年份
     *
     * @param date
     * @return
     */
    public int getYear(Date date) {
        return TimezoneUtil.getYear(date, getTimezone());
    }

    /**
     * 需要DateTime包含正确的时区
     *
     * @param dateTime
     * @return
     */
    public int getYear(DateTime dateTime) {
        return dateTime.get(DateTimeFieldType.year());
    }

    /**
     * 获取指定date在指定timezone下的月份
     *
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        return TimezoneUtil.getMonth(date, getTimezone());
    }

    /**
     * 需要DateTime包含正确的时区
     *
     * @param dateTime
     * @return
     */
    public int getMonth(DateTime dateTime) {
        return dateTime.get(DateTimeFieldType.monthOfYear());
    }

    /**
     * 获取指定date在指定timezone下的天
     *
     * @param date
     * @return
     */
    public int getDay(Date date) {
        return TimezoneUtil.getDay(date, getTimezone());
    }

    public int getHour(Date date) {
        return TimezoneUtil.getHour(date, getTimezone());
    }

    public int getMinute(Date date) {
        return TimezoneUtil.getMinute(date, getTimezone());
    }

    public int getSecond(Date date) {
        return TimezoneUtil.getSecond(date, getTimezone());
    }

    /**
     * 获得endDay之前n天的DateTime
     *
     * @param endDay 必须符合yyyy-MM-dd格式
     * @param n
     * @return
     */
    public List<DateTime> getPreviousNDays(String endDay, int n) {
        DateTime endDateTime = parse(endDay);
        List<DateTime> dateTimes = Lists.newArrayListWithExpectedSize(n);
        dateTimes.add(endDateTime);
        int index = n;
        DateTime dateTime = endDateTime;
        while (--index > 0) {
            dateTime = dateTime.minusDays(1);
            dateTimes.add(dateTime);
        }
        return dateTimes;
    }

    /**
     * 分页
     *
     * @param startDay
     * @param endDay
     * @param pageSize
     * @param pageNo
     * @return
     */
    public PageRes<List<DateTime>> pageDays(String startDay, String endDay, int pageSize, int pageNo) {
        DateTime startDateTime = parse(startDay);
        DateTime endDateTime = parse(endDay);
        checkDate(startDateTime, endDateTime);
        int offset = (pageNo - 1) * pageSize;
        DateTime candidate = endDateTime.minusDays(offset);
        List<DateTime> dateTimes = Lists.newArrayListWithExpectedSize(pageSize);
        dateTimes.add(candidate);
        int index = pageSize;
        while (--index > 0 && compare(candidate, startDateTime) > 0) {
            candidate = candidate.minusDays(1);
            dateTimes.add(candidate);
        }
        int total = daysInterval(startDateTime, endDateTime) + 1;
        return PageRes.instance(total, pageNo, pageSize, dateTimes);
    }

    public static int daysInterval(DateTime dateTime1, DateTime dateTime2) {
        return Math.abs(dateTime2.getDayOfYear() - dateTime1.getDayOfYear());
    }

    /**
     * 根据起止时间，返回一系列周的pair
     * 如<2019-02-03,2019-02-09>代表一周
     * startDay=2019-02-02, endDay=2019-02-10的话，返回三周数据
     *
     * @param startDay
     * @param endDay
     * @param pageSize
     * @param pageNo
     * @return
     */
    public PageRes<List<Pair<DateTime, DateTime>>> pageWeeks(String startDay, String endDay, int pageSize, int pageNo) {
        DateTime startDateTime = parse(startDay);
        DateTime endDateTime = parse(endDay);
        checkDate(startDateTime, endDateTime);
        List<Pair<DateTime, DateTime>> weeks = Lists.newArrayList();
        DateTime candidate = endDateTime;
        weeks.add(getWeekOfDay(candidate));
        candidate = candidate.minusDays(DAYS_OF_WEEK);
        while (compare(startDateTime, candidate) < 0) {
            weeks.add(getWeekOfDay(candidate));
            candidate = candidate.minusDays(DAYS_OF_WEEK);
        }
        if (!dayInWeek(startDateTime, weeks.get(weeks.size() - 1))) {
            weeks.add(getWeekOfDay(startDateTime));
        }
        int from = (pageNo - 1) * pageSize;
        if (from >= weeks.size()) {
            return PageRes.instance(0, pageNo, pageSize, Collections.emptyList());
        }
        int to = Math.min(pageNo * pageSize, weeks.size());
        return PageRes.instance(weeks.size(), pageNo, pageSize, weeks.subList(from, to));
    }

    /**
     * 根据起止时间，返回一系列月份pair
     *
     * @param startDay
     * @param endDay
     * @param pageSize
     * @param pageNo
     * @return
     */
    public PageRes<List<Pair<DateTime, DateTime>>> pageMonths(String startDay, String endDay, int pageSize, int pageNo) {
        DateTime startDateTime = parse(startDay);
        DateTime endDateTime = parse(endDay);
        checkDate(startDateTime, endDateTime);
        List<Pair<DateTime, DateTime>> months = Lists.newArrayList();
        months.add(getMonthOfDay(endDateTime));
        DateTime candidate = months.get(months.size() - 1).getKey();
        while (compare(startDateTime, candidate) < 0) {
            months.add(getMonthOfDay(candidate.minusDays(1)));
            candidate = months.get(months.size() - 1).getKey();
        }
        int from = (pageNo - 1) * pageSize;
        if (from >= months.size()) {
            return PageRes.instance(0, pageNo, pageSize, Collections.emptyList());
        }
        int to = Math.min(pageNo * pageSize, months.size());
        return PageRes.instance(months.size(), pageNo, pageSize, months.subList(from, to));
    }

    private boolean dayInWeek(DateTime dateTime, Pair<DateTime, DateTime> week) {
        return compare(week.getValue(), dateTime) >= 0
                && compare(dateTime, week.getKey()) >= 0;
    }

    public Pair<DateTime, DateTime> getWeekOfDay(DateTime dateTime) {
        int pos = getDayOfWeek(dateTime);
        DateTime firstDay = getFirstDayOfWeek(dateTime, pos);
        DateTime lastDay = getLastDayOfWeek(dateTime, pos);
        return Pair.of(firstDay, lastDay);
    }

    public Pair<DateTime, DateTime> getMonthOfDay(DateTime dateTime) {
        return Pair.of(firstDayOfMonth(dateTime), lastDayOfMonth(dateTime));
    }

    private DateTime getFirstDayOfWeek(DateTime dateTime, int pos) {
        return dateTime.minusDays(pos - 1);
    }

    private DateTime getLastDayOfWeek(DateTime dateTime, int pos) {
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

    private void checkDate(DateTime dateTime1, DateTime dateTime2) {
        if (compare(dateTime1, dateTime2) >= 0) {
            throw new IllegalStateException("start date should be later than end date");
        }
    }

    public long compare(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getMillis() - dateTime2.getMillis();
    }


    /**
     * 计算指定日期在一年当中是第几周
     *
     * @param date
     * @return
     */
    public int getWeekOfYear(Date date) {
        return TimezoneUtil.weekOfYear(date, properties.getValue());
    }

    /**
     * 需要DateTime包含正确的时区
     *
     * @param dateTime
     * @return
     */
    public int getWeekOfYear(DateTime dateTime) {
        return dateTime.get(DateTimeFieldType.weekOfWeekyear());
    }

    /**
     * 返回该天是一周的第几天，周日为第一天
     *
     * @param dateTime
     * @return
     */
    public int getDayOfWeek(DateTime dateTime) {
        Calendar calendar = dateTime.toCalendar(Locale.ENGLISH);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将yyyy-MM-dd格式的字符串转换成DateTime
     *
     * @param date
     * @return
     */
    public DateTime parse(String date) {
        return DateTime.parse(getParseFormat(date));
    }

    private String getParseFormat(String day) {
        String offset = getOffset();
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
}
