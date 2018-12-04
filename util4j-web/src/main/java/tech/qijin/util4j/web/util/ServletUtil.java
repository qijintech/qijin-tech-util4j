package tech.qijin.util4j.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class ServletUtil {

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
