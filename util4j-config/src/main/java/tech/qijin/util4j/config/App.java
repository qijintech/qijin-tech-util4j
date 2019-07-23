package tech.qijin.util4j.config;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(20);
        BigDecimal b2 = new BigDecimal("20.00");
        log.info("res={}", b1.equals(b2));
        log.info("res={}", b1.compareTo(b2));

        String t = " 03";
        log.info("res={}", Integer.valueOf(StringUtils.trim(t)));
        int i = 0;
        while (i++ < 3) {
            System.out.println(i);
        }

        Map<String, String> map = Maps.newHashMap();
        map.values().stream().forEach(v -> System.out.println(v));

        String v1 = "1.2.24";
        String v2 = "1.2.25";
        log.info("res={}", versionCompare(v1, v2));

    }

    public static int versionCompare(String v1, String v2) {
        log.info("v1={}", v1);
        log.info("v2={}", v2);

        if (v1.equals(v2)) {
            return 0;
        }
        String[] v1Arr = v1.split("\\.");
        String[] v2Arr = v2.split("\\.");
//        log.info("ss={}", Lists.newArrayList(v1Arr));
//        log.info("ss={}", v2Arr);
        Preconditions.checkArgument(v1Arr.length == 3 && v2Arr.length == 3);
        int i = 0;
        while (i++ < 3) {
            if (Integer.valueOf(StringUtils.trim(v1Arr[i - 1])) > Integer.valueOf(StringUtils.trim(v2Arr[i - 1]))) {
                return 1;
            } else if (Integer.valueOf(StringUtils.trim(v1Arr[i - 1])) < Integer.valueOf(StringUtils.trim(v2Arr[i - 1]))) {
                return -1;
            }
        }
        return 0;
    }
}
