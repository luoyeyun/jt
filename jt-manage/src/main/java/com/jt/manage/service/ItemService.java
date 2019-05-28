package com.jt.manage.service;

import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;

public interface ItemService {
    EasyUIResult findItemByPage(Integer page, Integer rows);

    String findItemCatNameById(Long id);

    void deleteItems(String ids);

    void saveItem(Item item, String desc);

    ItemDesc findItemDescById(Long itemId);

    void instockItem(String ids);
}
