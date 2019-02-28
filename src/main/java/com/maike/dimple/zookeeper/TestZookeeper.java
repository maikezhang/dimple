package com.maike.dimple.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/7/3
 * Time: 上午10:29
 */
public class TestZookeeper implements Watcher {

    ZooKeeper zooKeeper;
    String    hostPort;

    List<ACL> acls =new ArrayList<>();

    ACL acl =new ACL();

    String status;




    Random random=new Random();
    String servierId=Integer.toHexString(random.nextInt());


    public TestZookeeper(String hostPort){
        this.hostPort=hostPort;
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    void startZK(){

        try {
            zooKeeper=new ZooKeeper(hostPort,15000,this);

        }catch (Exception e){
            System.out.println("失败:"+e);
        }
    }


    void stopZK() throws InterruptedException {
        zooKeeper.close();
    }


    void register(){

        zooKeeper.create("/workers/woreker-"+servierId,"maikezhang".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,createWorkerCallback,null);
    }
    AsyncCallback.StringCallback createWorkerCallback =new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
                  switch (KeeperException.Code.get(rc)){
                      case OK:
                          System.out.println("Registered successfully:"+servierId);
                          break;
                      case CONNECTIONLOSS:
                          register();
                          break;
                      case NODEEXISTS:
                          System.out.println("Already registered: "+servierId);
                          break;
                      default:
                          System.out.println("Someting went wrong:"+ KeeperException.create(KeeperException.Code.get(rc),path));
                  }
        }
    };


    synchronized private void updateSstatus(String status){
        if(status==this.status){
            zooKeeper.setData("/workers/worker"+servierId,status.getBytes(),-1,statusUpdateCallback,status);
        }
    }

    AsyncCallback.StatCallback statusUpdateCallback =new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)){

                case CONNECTIONLOSS:
                    updateSstatus((String)ctx);
                    return;
            }
        }
    };

    public void setStatus(String status){
        this.status=status;
        updateSstatus(status);
    }



    public List<String> getNode() throws KeeperException, InterruptedException {
        return zooKeeper.getChildren("/dubbo",this);

    }


//    public static void main(String[] args) throws Exception {
//
//        System.out.println(args[0]);
//        TestZookeeper testZookeeper=new TestZookeeper(args[0]);
//
//        testZookeeper.startZK();
//
//        testZookeeper.register();
//
//        testZookeeper.setStatus("123");
//
//
//        Thread.sleep(30000);
//
//        testZookeeper.stopZK();
//
//    }
}
