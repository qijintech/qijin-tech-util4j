package tech.qijin.util4j.web.interceptor;

/**
 * @author michealyang
 * @date 2018/11/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class InterceptorExclusion {
    public static final String FAVICON = "/favicon.ico";
    public static final String STATIC = "/static/*";

    public static final String[] COMMON = {FAVICON, STATIC};
}
