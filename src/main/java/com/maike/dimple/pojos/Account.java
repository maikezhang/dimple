package com.maike.dimple.pojos;

import com.maike.dimple.common.Base;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/4/28
 * Time: 下午3:59
 */
public class Account extends Base {


    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
