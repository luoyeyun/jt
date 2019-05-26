package com.jt.manage.service.impl;

import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public EasyUIResult findItemByPage(Integer page, Integer rows) {

        //商品总记录数
        //int total = itemMapper.findItemCount();
        //通用Mapper查询
        int total = itemMapper.selectCount(null);
        //分页查询起始位置
        int start = (page-1)*rows;
        //商品列表
        List<Item> items = itemMapper.findItemByPage(start,rows);


        return new EasyUIResult(total,items);
    }

    @Override
    public String findItemCatNameById(Long id) {
        return itemMapper.findItemCatNameById(id);
    }

    @Override
    @Transactional
    public void saveItem(Item item,String desc) {
        //保存商品信息
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus(1);//默认设置正常状态
        itemMapper.insert(item);

        //保存商品详情信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        itemDescMapper.insert(itemDesc);

    }


    /**
     * 基于商品ID查询商品详情信息
     * @param itemId    商品id
     * @return  商品详情信息
     */
    @Override
    public ItemDesc findItemDescById(Long itemId) {
        ItemDesc itemDesc = itemDescMapper.selectItemDescById(itemId);
        return itemDesc;
    }

    /**
     * 基于商品ID删除商品
     * @param ids
     */
    @Transactional
    @Override
    public void deleteItems(String ids) {
        String[] itemIds = ids.split(",");
        //通用Mapper方法删除商品
        itemMapper.deleteByIDS(itemIds);
    }
}
