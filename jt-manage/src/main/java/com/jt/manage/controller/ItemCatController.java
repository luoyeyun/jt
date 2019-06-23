package com.jt.manage.controller;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * @RequestParam 动态获取参数，有则为ID，无则默认值为OL
     */
    @RequestMapping("/cat/list")
    @ResponseBody
    public List<EasyUI_Tree> findTree(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        //展示一级列表信息
        //Long parentId = 0L;

       // List<EasyUI_Tree> list = itemCatService.findTree(parentId);
        List<EasyUI_Tree> list = itemCatService.findTreeCache(parentId);
        return list;
    }

}
