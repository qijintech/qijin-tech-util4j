package tech.qijin.util4j.utils;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

/**
 * @author michealyang
 * @date 2019/2/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class IpUtil {

    private static final String UNKNOWN = "unknown";
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final String IP_SEPARATOR = ",";
    private static final int IP_LENGTH = 15;

    /**
     * 从request中获取真实的ip地址
     *
     * @param request
     * @return
     * @refer https://blog.csdn.net/xiaokui_wingfly/article/details/45888381
     */
    public static String getIpv4(HttpServletRequest request) {
        try {
            String ipAddress = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals(LOCAL_HOST)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (StringUtils.isNotBlank(ipAddress) && ipAddress.length() > IP_LENGTH) {
                int index = ipAddress.indexOf(IP_SEPARATOR);
                if (index > 0) {
                    ipAddress = ipAddress.substring(0, index);
                }
            }
            return ipAddress;
        } catch (Exception e) {
            log.error(LogFormat.builder().message("parse ip exception").build(), e);
        }

        return "";
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
