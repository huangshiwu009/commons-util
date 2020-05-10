package com.perfecton.core.util;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author Huang ShiWu
 * @data 2020/03/23 16:55
 * @email 673724206@qq.com
 */
public class IpUtil {

    private final static String UNKONWN = "unknown";

    /**
     * 使用Nginx做反向代理时，使用该方法
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            String[] ips = ip.split(",");
            for (String oneIp : ips) {
                if (isIpV4Valid(oneIp) && !isIpV4Private(oneIp)) {
                    return oneIp;
                }
            }
        }
        ip = request.getRemoteAddr();
        return ip;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isEmptyOrUnknow(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isEmptyOrUnknow(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isEmptyOrUnknow(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isEmptyOrUnknow(String str) {
        if (str == null || str.length() == 0 || UNKONWN.equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    private static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    private static final Pattern PATTERN = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");

    public static String longToIpV4(long longIp) {

        int octet3 = (int) ((longIp >> 24) % 256);
        int octet2 = (int) ((longIp >> 16) % 256);
        int octet1 = (int) ((longIp >> 8) % 256);
        int octet0 = (int) (longIp % 256);
        return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
    }

    public static long ipV4ToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Long.parseLong(octets[1]) << 16) + (Long.parseLong(octets[2]) << 8) +
                Long.parseLong(octets[3]);
    }

    public static boolean isIpV4Private(String ip) {
        long longIp = ipV4ToLong(ip);
        return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255")) ||
                (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255")) ||
                (longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255"));
    }

    public static boolean isIpV4Valid(String ip) {
        return PATTERN.matcher(ip).matches();
    }

    /**
     * 获取服务器ip地址
     * 针对：linux下获取IP的方式有时候会得到127.0.0.1
     * 对网络接口进行筛选,非回送接口 且 非虚拟网卡 且 正在使用中
     *
     * @return
     */
    public static String getServerIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("IP地址获取失败");
        return null;
    }


}
