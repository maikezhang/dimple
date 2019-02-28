package com.maike.dimple.common;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/1/1
 * Time: 下午10:04
 */
public class Base implements Serializable {

    protected static final Gson GSON=new Gson();

    @Override
    public String toString(){
        return GSON.toJson(this);
    }
}
