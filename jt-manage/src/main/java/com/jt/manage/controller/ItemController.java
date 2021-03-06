package com.jt.manage.controller;

import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 分页查询商品
     *
     * @param page 当前页数
     * @param rows 每页显示记录数
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public EasyUIResult findItemByPage(Integer page, Integer rows, HttpServletRequest request) {
        return itemService.findItemByPage(request,page, rows);
    }

    /**
     * 分页查询已删除商品
     *
     * @param page 当前页数
     * @param rows 每页显示记录数
     * @return
     */
    @RequestMapping("/queryDeletedItems")
    @ResponseBody
    public EasyUIResult queryDeletedItems(Integer page, Integer rows, HttpServletRequest request) {
        return itemService.queryDeletedItems(request,page,rows);
    }

    /**
     * 查询商品分类名称
     *
     * @param itemCatId 商品分类ID
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryItemCatName", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String findItemCatNameById(Long itemCatId, HttpServletResponse response) {
        String itemName = itemService.findItemCatNameById(itemCatId);
        return itemName;
    }

    /**
     * 保存新增商品信息
     *
     * @param item 商品
     * @param desc 商品详情
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public SysResult saveItem(Item item, String desc) {
        try {
            if (item.getCid() == null) {
                return SysResult.build(201, "请选择商品分类");
            }
            itemService.saveItem(item, desc);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "保存商品失败");
    }

    /**
     * 基于商品ID删除商品
     *
     * @param ids 商品id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public SysResult deleteItems(Long[] ids) {
        /*try {
            itemService.deleteItems(ids);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        int status = 3;
        try {
            itemService.updateItemStatusByIds(ids,status);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "删除失败");
    }

    /**
     * 更新商品
     * @param item  商品基础信息
     * @param desc  商品详情信息
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public SysResult updateItem(Item item, String desc) {
        try {
            if (item.getCid() == null) {
                return SysResult.build(201, "商品分类不能为空");
            }
            itemService.updateItem(item, desc);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "保存商品失败");
    }


    /**
     * 基于商品ID查询商品详情信息
     *
     * @param itemId 商品id
     * @return 商品详情信息
     */
    @RequestMapping("/query/item/desc/{itemId}")
    @ResponseBody
    public SysResult findItemDescById(@PathVariable Long itemId) {
        try {
            ItemDesc itemDesc = itemService.findItemDescById(itemId);
            return SysResult.oK(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "查询数据失败");
    }

    /**
     * 商品下架
     *
     * @param ids 商品id
     * @return
     */
    @RequestMapping("/instock")
    @ResponseBody
    public SysResult instockItem(Long[] ids) {
        int status = 2;
        try {
            itemService.updateItemStatusByIds(ids,status);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "商品下架失败，请稍后再试");
    }


    @RequestMapping("/reshelf")
    @ResponseBody
    public SysResult reshelfItems(Long[] ids){
        int status = 1;
        try {
            itemService.updateItemStatusByIds(ids,status);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "商品上架失败，请稍后再试");
    }

    @RequestMapping("/regain")
    @ResponseBody
    public SysResult regainItems(Long[] ids){
        int status = 2;
        try {
            itemService.updateItemStatusByIds(ids,status);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "商品恢复失败，请稍后再试");
    }

    /**
     * 基于商品ID删除回收站商品
     * @param ids 商品id
     * @return
     */
    @RequestMapping("/recycleBin/delete")
    @ResponseBody
    public SysResult deleteIRecycleBintems(Long[] ids) {
        try {
            itemService.deleteIRecycleBintems(ids);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "删除商品失败");
    }
}
