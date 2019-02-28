package com.maike.dimple.mapper;

import com.maike.dimple.pojos.Item;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangmaike on 2018/4/28.
 */
public interface ItemMapper {


    Item get(Long id);

    int update(@Param("item") Item item);


}
