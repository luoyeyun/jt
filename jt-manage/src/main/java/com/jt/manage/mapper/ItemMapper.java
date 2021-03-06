package com.jt.manage.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper extends SysMapper<Item> {
    //@Select("select count(*) from tb_item")
    //int findItemCount();

    List<Object> findItemByPage(
            @Param("id") String id,
            @Param("title") String title,
            @Param("status") String ststus,
            @Param("startTime")String startTime,
            @Param("endTime")String endTime,
            @Param("start") Integer start,
            @Param("rows") Integer rows);

    @Select("select name from tb_item_cat where id=#{id}")
    String findItemCatNameById(Long id);


    void updateItemStatusByIds(@Param("ids") Long[] ids,@Param("status") Integer status);

    List<Object> queryDeletedItems(@Param("id") String id,
                                   @Param("title") String title,
                                   @Param("startTime")String startTime,
                                   @Param("endTime")String endTime,
                                   @Param("start") Integer start,
                                   @Param("rows") Integer rows);
}
