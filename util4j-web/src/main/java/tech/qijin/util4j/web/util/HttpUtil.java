package tech.qijin.util4j.web.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public final class HttpUtil {

    /**
     * 存放格式
     * <token,session>
     * @param httpServletRequest
     * @return
     */
    public static  String getToken(HttpServletRequest httpServletRequest){
       return httpServletRequest.getHeader("token");
    }



    public static String getHost(HttpServletRequest request) {
        String host = request.getHeader("X-Forwarded-Host");
        if (host == null) {
            host = request.getHeader("Host");
        }
        return host;
    }


    public static String getSchemaAndHost(HttpServletRequest request) throws UnsupportedEncodingException {
        String host = getHost(request);
        String schema = request.getScheme();
        return schema + "://" + host;
    }

    public static String getUri(HttpServletRequest request){
        return request.getRequestURI();
    }


}
