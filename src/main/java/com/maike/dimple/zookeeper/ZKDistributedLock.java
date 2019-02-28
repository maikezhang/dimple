package com.maike.dimple.zookeeper;

import lombok.Getter;
import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * classname：DistributedLock
 * desc：基于zookeeper的开源客户端Cruator实现分布式锁
 * author：zhangyingjie
 */
@Getter
@Setter
public class ZKDistributedLock {
    public static Logger log = LoggerFactory.getLogger(ZKDistributedLock.class);
    private InterProcessMutex interProcessMutex;  //可重入排它锁
    private String            lockName;  //竞争资源标志
    private String root = "/distributed/lock/";//根节点
    private static CuratorFramework curatorFramework;
    private  String ZK_URL;

    private Integer sleepTimeMs;

    private Integer maxRetries;


    public ZKDistributedLock() {

    }

    public void init() {
        curatorFramework = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(sleepTimeMs, maxRetries));
        curatorFramework.start();
    }


    private void initInterProcessMutex() {
        try {
            interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
        } catch (Exception e) {
            log.error("initial InterProcessMutex exception=" + e);
        }
    }

    private ZKDistributedLock(String lockName){
        this.lockName=lockName;
        try {
            interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
        } catch (Exception e) {
            log.error("initial InterProcessMutex exception=" + e);
        }
    }

    public static ZKDistributedLock getInstence(String lockName){
        return new ZKDistributedLock(lockName);
    }

    /**
     * 获取锁  默认重试2次
     */
    public boolean acquireLock() {
//        initInterProcessMutex();
        int flag = 0;
        try {
            //重试2次，每次最大等待2s，也就是最大等待4s
            while (!interProcessMutex.acquire(5, TimeUnit.SECONDS)) {
                flag++;
                if (flag > 1) {  //重试两次
                    break;
                }
            }
        } catch (Exception e) {
            log.error("distributed lock acquire exception=" + e);
        }
        if (flag > 1) {
            log.info("acquire distributed lock  busy");
            return false;
        } else {
            log.info("acquire distributed lock  success");
            return true;
        }
    }

    /**
     * @param timeOut  等待时间 时间单位为s 最终的等待时间根据重试次数来算  timeOut*retries
     * @param retries  获取锁的重试次数
     */
    public boolean acquireLock( long timeOut, int retries) {

        int flag = 1;
        try {
            while (!interProcessMutex.acquire(timeOut, TimeUnit.SECONDS)) {
                if (flag > retries) {
                    break;
                }
                flag++;
            }
        } catch (Exception e) {
            log.error("distributed lock acquire exception=" + e);
            return false;
        }

        if (flag > retries) {
            log.info("acquire distributed lock  busy");
            return false;
        } else {
            log.info("acquire distributed lock  success");
            return true;
        }


    }

    /**
     * 释放锁
     */
    public void releaseLock() {
        try {
            if (interProcessMutex != null && interProcessMutex.isAcquiredInThisProcess()) {
                interProcessMutex.release();
                curatorFramework.delete().inBackground().forPath(root + lockName);
                log.info(" release distributed lock  success");
            }
        } catch (Exception e) {
            log.info(" release distributed lock  exception=" + e);
        }
    }
}
