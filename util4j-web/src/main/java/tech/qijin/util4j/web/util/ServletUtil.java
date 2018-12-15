package tech.qijin.util4j.web.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.web.filter.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class ServletUtil {

    private static final String[] POST_CONTENT_TYPE = {
            "application/json",
            "application/x-www-form-urlencoded",
            "multipart/form-data",
            "text/xml"};

    public static boolean isGetMethod(HttpServletRequest request) {
        return "GET".equalsIgnoreCase(request.getMethod());
    }

    public static boolean isPostMethod(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod());
    }

    public static Map<String, String[]> getParameters(RequestWrapper request) {
        if (isGetMethod(request)) {
            return request.getParameterMap();
        }
        if (isPostMethod(request)) {
            if (POST_CONTENT_TYPE[1].equalsIgnoreCase(request.getContentType())) {
                return request.getParameterMap();
            }
            if (POST_CONTENT_TYPE[0].equalsIgnoreCase(request.getContentType())) {
                JSONObject jsonObject = JSONObject.parseObject(request.getBodyString());
                Map<String, Object> tmp = JSONObject.toJavaObject(jsonObject, Map.class);
                return tmp.entrySet().stream().collect(
                        Collectors.toMap(Map.Entry::getKey, entry -> new String[]{String.valueOf(entry.getValue())}));
            }
        }
        return Collections.emptyMap();
    }

    public static String getUrl(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    public static String getUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public static String getHost(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();
        return url.substring(0, url.indexOf(uri));
    }

    public static String getSchme(HttpServletRequest request) {
        return request.getScheme();
    }
}
