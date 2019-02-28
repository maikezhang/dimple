package com.maike.dimple.nacos.listener;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/2/21
 * Time: 下午4:33
 */
@Configuration
public class NacosTestListener {

    private static final Log LOGGER= LogFactory.getLog(NacosTestListener.class);


    @NacosConfigListener(dataId = "maike")
    public void onReceived(Properties properties){

        LOGGER.info("onReceived  properties:{}"+properties.toString());
    }
}
