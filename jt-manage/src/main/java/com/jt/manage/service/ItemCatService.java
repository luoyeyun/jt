package com.jt.manage.service;

import com.jt.manage.vo.EasyUI_Tree;

import java.util.List;

public interface ItemCatService {

    List<EasyUI_Tree> findTree(Long parentId);

    List<EasyUI_Tree> findTreeCache(Long parentId);
}
