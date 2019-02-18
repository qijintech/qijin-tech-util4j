package tech.qijin.util4j.timezone;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.timezone.config.TimezoneProperties;
import tech.qijin.util4j.utils.TimezoneUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author michealyang
 * @date 2019/2/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class TimezoneHandler {
    @Autowired
    private TimezoneProperties properties;

    /**
     * 将指定date转换成指定时区的Date。时区信息从配置文件中读取timezone.value变量
     * <p>还未实现</p>
     *
     * @param date
     * @return
     */
    public DateTime coordinate(Date date) {
        return TimezoneUtil.coordinate(date, properties.getValue());
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
        return TimezoneUtil.toString(date, properties.getValue(), format);
    }

    /**
     * 获取指定date在指定timezone下的年份
     *
     * @param date
     * @return
     */
    public int getYear(Date date) {
        return TimezoneUtil.getYear(date, properties.getValue());
    }

    /**
     * 获取指定date在指定timezone下的月份
     *
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        return TimezoneUtil.getMonth(date, properties.getValue());
    }

    /**
     * 获取指定date在指定timezone下的天
     *
     * @param date
     * @return
     */
    public int getDay(Date date) {
        return TimezoneUtil.getDay(date, properties.getValue());
    }

    public int getHour(Date date) {
        return TimezoneUtil.getHour(date, properties.getValue());
    }

    public int getMinute(Date date) {
        return TimezoneUtil.getMinute(date, properties.getValue());
    }

    public int getSecond(Date date) {
        return TimezoneUtil.getSecond(date, properties.getValue());
    }

    /**
     * 计算指定日期在一年当中是第几周
     *
     * @param date
     * @param timezone
     * @return
     */
    public int getWeekOfYear(Date date, String timezone) {
        return TimezoneUtil.getWeekOfYear(date, properties.getValue());
    }
}
