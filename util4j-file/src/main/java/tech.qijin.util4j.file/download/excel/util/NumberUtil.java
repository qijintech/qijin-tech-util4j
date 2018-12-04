package tech.qijin.util4j.file.download.excel.util;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/11
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
public class NumberUtil {


    /**
     * 判断浮点数是不是整数
     *
     * @param number 数字
     * @return
     */
    public static boolean isInteger(double number) {
        return (number * 10) % 10 == 0;
    }


    /**
     * 浮点数Str处理
     * 1.5显示=>1.5
     * 1.0显示=>1
     *
     * @param number 浮点数
     * @return
     */
    public static String numberStr(Double number) {
        if (isInteger(number)) {
            return "" + number.intValue();
        }
        return "" + number;
    }

}