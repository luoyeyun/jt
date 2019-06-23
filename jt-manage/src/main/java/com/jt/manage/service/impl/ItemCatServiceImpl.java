package com.jt.manage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ItemCat;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private JedisCluster jedisCluster;


    /**
     * 查询目录分类
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUI_Tree> findTree(Long parentId) {
        List<ItemCat> catList = findItemCatByParentId(parentId);
        List<EasyUI_Tree> treeList = new ArrayList<EasyUI_Tree>();
        for (ItemCat itemCat : catList) {
            EasyUI_Tree tree = new EasyUI_Tree();
            tree.setId(itemCat.getId());
            tree.setText(itemCat.getName());
            String state = itemCat.getIsParent() ? "closed" : "open";
            tree.setState(state);
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 基于redis查询目录分类
     *
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUI_Tree> findTreeCache(Long parentId) {

        /*Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.3.2", 7000));
        nodes.add(new HostAndPort("192.168.3.2", 7001));
        nodes.add(new HostAndPort("192.168.3.2", 7002));
        nodes.add(new HostAndPort("192.168.3.2", 7003));
        nodes.add(new HostAndPort("192.168.3.2", 7004));
        nodes.add(new HostAndPort("192.168.3.26", 7005));
        JedisCluster jedis = new JedisCluster(nodes);*/


        String key = "ITEM_CAT_" + parentId;
        String result = jedisCluster.get(key);
        List<EasyUI_Tree> treeList = new ArrayList<EasyUI_Tree>();
        try {
            if (StringUtils.isEmpty(result)) {
                treeList = findTree(parentId);
                String listJSON = objectMapper.writeValueAsString(treeList);
                jedisCluster.set(key, listJSON);
            } else {
                treeList = objectMapper.readValue(result, treeList.getClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return treeList;
    }

    /**
     * 基于parentId查询商品目录
     *
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
