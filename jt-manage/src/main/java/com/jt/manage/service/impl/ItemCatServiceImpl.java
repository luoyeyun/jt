package com.jt.manage.service.impl;

import com.jt.common.po.ItemCat;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<EasyUI_Tree> findTree(Long parentId) {
        List<ItemCat> catList = findItemCatByParentId(parentId);
        List<EasyUI_Tree> treeList = new ArrayList<EasyUI_Tree>();
        for (ItemCat itemCat: catList) {
            EasyUI_Tree tree = new EasyUI_Tree();
            tree.setId(itemCat.getId());
            tree.setText(itemCat.getName());
            String state = itemCat.getIsParent()?"closed":"open";
            tree.setState(state);
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 基于parentId查询商品目录
     * @param parentId
     * @return
     */
    private List<ItemCat> findItemCatByParentId(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        //基于通用mapper查询商品分类
        List<ItemCat> catList = itemCatMapper.select(itemCat);
        return catList;
    }
}
