package com.maike.dimple.common;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.maike.dimple.utils.IpAddrUtil;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/1/17
 * Time: 下午3:57
 */
public class IpAddrConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        return IpAddrUtil.getLocalIpAddr();
    }
}
