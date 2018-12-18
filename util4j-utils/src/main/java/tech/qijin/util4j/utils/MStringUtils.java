package tech.qijin.util4j.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StringUtils扩展
 *
 * @author michealyang
 * @date 2018/12/18
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class MStringUtils {
    /**
     * 将一个String按照指定分隔符分隔，并将blank字符去除，字符前后的空格去除
     *
     * @param src
     * @param separator
     * @return
     */
    public static List<String> splitAndTrim(String src, String separator) {
        Preconditions.checkArgument(StringUtils.isNotBlank(src) && StringUtils.isNotBlank(separator),
                "invalid parameters");
        return Arrays.stream(src.split(separator))
                .filter(str -> StringUtils.isNotBlank(str))
                .map(str -> str.trim())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String test = "asdf, asdf , adf sf, sf,,f sdf";
        System.out.println(splitAndTrim(test, ","));
    }
}
