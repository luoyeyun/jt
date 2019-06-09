package com.jt.manage.service.impl;

import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;


    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EasyUIResult findItemByPage(HttpServletRequest request, Integer page, Integer rows) {

        //商品总记录数
        //int total = itemMapper.findItemCount();
        //通用Mapper查询
        //int total = itemMapper.selectCount(null);
        //分页查询起始位置
        int start = (page-1)*rows;
        //商品列表
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        List<Object> itemsList = itemMapper.findItemByPage(id,title,start,rows);
        List<Item> items = null;
        Long total = 0L;
        if (CollectionUtils.isNotEmpty(itemsList)){
            items = (List<Item>) itemsList.get(0);
            total = ((List<Long>) itemsList.get(1)).get(0);
        }


        return new EasyUIResult(total,items);
    }

    /**
     * 查询商品分类名称
     * @param id
     * @return
     */
    @Override
    public String findItemCatNameById(Long id) {
        return itemMapper.findItemCatNameById(id);
    }

    /**
     * 保存新增商品
     * @param item
     * @param desc
     */
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
        itemDesc.setItemDesc(desc);
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
     * 商品下架
     * @param ids
     */
    @Override
    public void updateItemStatusByIds(Long[] ids,Integer status) {
        itemMapper.updateItemStatusByIds(ids,status);
    }

    /**
     * 编辑商品
     * @param item  基础信息
     * @param desc  商品详情
     */
    @Override
    public void updateItem(Item item, String desc) {
        Date date = new Date();
        //更新基础信息
        item.setUpdated(date);
        itemMapper.updateByPrimaryKeySelective(item);

        //更新商品详情信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setUpdated(date);
        itemDesc.setItemDesc(desc);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
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
        itemDescMapper.deleteByIDS(itemIds);
    }
}
