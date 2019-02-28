package com.maike.dimple.bo;

import com.maike.dimple.mapper.ItemMapper;
import com.maike.dimple.pojos.Item;
import com.maike.dimple.zookeeper.ZKDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/1/16
 * Time: 下午4:25
 */

@Component
public class ItemBO {


    @Resource
    private ItemMapper itemMapper;

    public static Logger log = LoggerFactory.getLogger(ZKDistributedLock.class);

    public static int num= 10000;


    public String buy(Long id){


        Item item = itemMapper.get(id);
        if(Objects.isNull(item)){
            return "对不起你要购买的商品不存在！";
        }
        String result;
//        ZKDistributedLock distributedLock=null;
        ZKDistributedLock zkDistributedLock = ZKDistributedLock.getInstence("demo");
        try {
//            distributedLock=new ZKDistributedLock("demo");
            boolean lock = zkDistributedLock.acquireLock();
            if(!lock){
                return "系统超时购买失败";
            }
//            log.info("------开始购买-------");
//            Integer stock = item.getStock();
//            if (stock > 0) {
//                stock--;
//                Item item1=new Item();
//                item1.setId(id);
//                item1.setStock(stock);
//                itemMapper.update(item1);
//                result="恭喜你购买成功";
//            }else{
//                result="对不起，您购买的商品已经卖完了";
//            }
            if(num>0){
                num--;
                result="恭喜你购买成功,商品还剩"+num;
            }else{
                result="对不起，您购买的商品已经卖完了";
            }
            log.info("------购买结果"+result+"-------");
            log.info("------购买结束-------");
        }finally {
            zkDistributedLock.releaseLock();
        }


        return result;



    }

}
