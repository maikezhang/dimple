package com.maike.dimple.pojos;

import com.maike.dimple.common.Base;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2019/1/16
 * Time: 下午4:00
 */
@Setter
@Getter
public class Item extends Base {
    private Long id;
    private String title;
    private Integer stock;
}
