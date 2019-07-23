package tech.qijin.util4j.timezone;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.timezone.config.TimezoneProperties;
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
     * <p>还未实现</p>
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
     * 获取指定date在指定timezone下的月份
     *
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        return TimezoneUtil.getMonth(date, getTimezone());
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
     * 计算指定日期在一年当中是第几周
     *
     * @param date
     * @return
     */
    public int getWeekOfYear(Date date) {
        return TimezoneUtil.weekOfYear(date, getTimezone());
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
     * 获得endDay之前n天的DateTime
     *
     * @param endDay 必须符合yyyy-MM-dd格式
     * @param n
     * @return
     */
    public List<DateTime> getPreviousNDays(String endDay, int n) {
        DateTime endDateTime = DateTime.parse(getParseFormat(endDay));
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
