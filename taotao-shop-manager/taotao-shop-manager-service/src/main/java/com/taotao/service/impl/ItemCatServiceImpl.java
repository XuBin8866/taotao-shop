package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxbb
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUiTreeNode> getCatList(long parentId) {
        //1.根据parent id查询节点列表
        TbItemCatExample example=new TbItemCatExample();
            //设置查询条件
        TbItemCatExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
        //2.转换成EasyUiTreeNode列表
        List<EasyUiTreeNode> resultList = new ArrayList<>();
        for(TbItemCat tbItemCat:tbItemCats){
            EasyUiTreeNode node=new EasyUiTreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getParentId()==0?"closed":"open");
            resultList.add(node);
        }
        //3.返回值
        return resultList;
    }
}
