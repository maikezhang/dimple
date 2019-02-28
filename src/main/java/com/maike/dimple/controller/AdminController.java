package com.maike.dimple.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.maike.dimple.bo.ItemBO;
import com.maike.dimple.mapper.AdminMapper;
import com.maike.dimple.nacos.Configuration;
import com.maike.dimple.pojos.Admin;
import com.maike.dimple.pojos.Item;
import com.maike.dimple.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2017/11/18
 * Time: 下午7:48
 */
@Controller
@RequestMapping(value = "admin")
@ResponseBody
public class AdminController {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private ItemBO itemBO;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Response<Admin> get(){
        Admin admin=adminMapper.get(1L);
//            Admin admin =new Admin();
//            admin.setId(1L);
//            admin.setMobile("121212");
//            admin.setUsername("maike");


        return Response.ok(admin);
    }


    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Response register(Admin admin){
        return null;


    }


    @RequestMapping(value = "buy",method = RequestMethod.POST)
    public Response buy(@RequestBody Item item){

        String buy = itemBO.buy(item.getId());




        return Response.ok(buy);
    }



    @Resource
    private Configuration configuration;

//    @NacosValue(value = "${useLocalCache:false}",autoRefreshed = true)
//    public boolean useLocalCache;

    @RequestMapping(value = "/getNacos", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getuseLocalCache() {


        Map<String,Object> result=new HashMap<>();
        result.put("redishost",configuration.redisHost);
        result.put("useLocalCache",configuration.useLocalCache);

        return result;
    }



}
