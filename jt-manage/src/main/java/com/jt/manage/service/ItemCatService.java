package com.jt.manage.service;

import com.jt.manage.vo.EasyUI_Tree;

import java.util.List;

public interface ItemCatService {

    List<EasyUI_Tree> findTree(Long parentId);
}
