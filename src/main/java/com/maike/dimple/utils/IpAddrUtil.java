package com.maike.dimple.utils;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/1/17
 * Time: 下午3:58
 */
public class IpAddrUtil {
    private static final Logger  LOGGER           = LoggerFactory.getLogger(IpAddrUtil.class);
    private static final Pattern IPV4_PATTERN     = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final String  DEFAULT_LOCAL_IP = "127.0.0.1";
    private static final String  DEFAULT_NIC      = "eth0";
    private static final String  DEFAULT_NIC_MAC  = "en0";

    public static String getLocalIpAddr() {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName(DEFAULT_NIC);
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName(DEFAULT_NIC_MAC);
            }
            if (networkInterface == null) {
                return DEFAULT_LOCAL_IP;
            }
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                String ipAddr = addresses.nextElement().getHostAddress();
                if (IPV4_PATTERN.matcher(ipAddr).matches()) {
                    return ipAddr;
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取本地ip地址异常", e);
        }
        return DEFAULT_LOCAL_IP;
    }


    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getRemoteAddr();
        LOGGER.debug("获取到客户端ip地址:{}", ip);
        return ip;
    }

    public static boolean isEmpty(String str) {
        return null == str || str.isEmpty();
    }
}

