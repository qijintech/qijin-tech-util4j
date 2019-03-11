package tech.qijin.util4j.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.InetAddressValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author michealyang
 * @date 2019/2/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class IpUtil {

    /**
     * 从request中获取真实的ip地址
     *
     * @param request
     * @return
     * @refer https://blog.csdn.net/xiaokui_wingfly/article/details/45888381
     */
    public static String getIpv4(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isValid(String ip) {
        return isValidIpv4(ip) || isValidIpv6(ip);
    }

    public static boolean isValidIpv4(String ip) {
        return InetAddressValidator.getInstance().isValidInet4Address(ip);
    }

    public static boolean isValidIpv6(String ip) {
        return InetAddressValidator.getInstance().isValidInet6Address(ip);
    }

    public static int str2Int(String ip) {
        Preconditions.checkArgument(isValidIpv4(ip));
        String[] ipPortions = ip.split("\\.");
        return (Integer.parseInt(ipPortions[0]) << 24)
                | (Integer.parseInt(ipPortions[1]) << 16)
                | (Integer.parseInt(ipPortions[2]) << 8)
                | Integer.parseInt(ipPortions[3]);
    }

    public static String int2Str(int ip) {
        return "";
    }

    public static boolean isSameSubnet(String ip1, String ip2, String mask) {
        int ip1Int = str2Int(ip1);
        int ip2Int = str2Int(ip2);
        return inRange(ip1, mask) && inRange(ip2, mask);
    }

    public static boolean inRange(String ip, String mask) {
        int ipInt = str2Int(ip);
        int maskIpInt = str2Int(MaskUtil.getIp(mask));
        int maskInt = MaskUtil.getInt(mask);
        return (ipInt & maskInt) == (maskIpInt & maskInt);
    }

    public static class MaskUtil {
        public static boolean isValid(String mask) {
            //TODO
            return true;
        }

        public static String getIp(String mask) {
            Preconditions.checkArgument(isValid(mask));
            return mask.replaceAll("/.*", "");
        }

        public static int getInt(String mask) {
            Preconditions.checkArgument(isValid(mask));
            int type = Integer.parseInt(mask.replaceAll(".*/", ""));
            return 0xFFFFFFFF << (32 - type);
        }
    }
}
