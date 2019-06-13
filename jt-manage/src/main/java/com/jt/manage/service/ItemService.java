package com.jt.manage.service;

import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import org.springframework.http.server.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

public interface ItemService {
    EasyUIResult findItemByPage(HttpServletRequest request, Integer page, Integer rows);

    String findItemCatNameById(Long id);

    void deleteIRecycleBintems(Long[] ids);

    void saveItem(Item item, String desc);

    ItemDesc findItemDescById(Long itemId);

    void updateItemStatusByIds(Long[] ids,Integer status);

    void updateItem(Item item, String desc);

    EasyUIResult queryDeletedItems(HttpServletRequest request, Integer page, Integer rows);
}
