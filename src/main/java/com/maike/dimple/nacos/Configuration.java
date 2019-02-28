package com.maike.dimple.nacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/2/21
 * Time: 下午4:08
 */
@Getter
@Setter
public class Configuration {

    @NacosValue(value = "${useLocalCache:false}",autoRefreshed = true)
    public boolean useLocalCache;



    @NacosValue(value = "${redishost}",autoRefreshed = true)
    public String redisHost;



}
