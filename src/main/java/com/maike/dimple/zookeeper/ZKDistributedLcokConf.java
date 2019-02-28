package com.maike.dimple.zookeeper;

import org.apache.curator.framework.CuratorFramework;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/1/18
 * Time: 上午10:33
 */
public class ZKDistributedLcokConf {

    private String            lockName;  //竞争资源标志
    private String root = "/distributed/lock/";//根节点
    private static String ZK_URL = "127.0.0.1:2181";
}
