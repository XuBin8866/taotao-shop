package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xxbb
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUiTreeNode> getContentCategoryList(long parentId) {
        //设置查询条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
        //封装成TreeNode
        List<EasyUiTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            //添加到列表
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addContentCategory(long parentId, String name) {
        //插入新的内容类型对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排名，取值范围：大于零的整数
        tbContentCategory.setSortOrder(1);
        //状态，可选值：1（正常），2（删除）
        tbContentCategory.setStatus(1);
        //修改时间
        Date modifiedDate = new Date();
        tbContentCategory.setCreated(modifiedDate);
        tbContentCategory.setUpdated(modifiedDate);
        //插入内容类型数据
        tbContentCategoryMapper.insert(tbContentCategory);
        //判断父节点的parent是否为true，不是true需要进行更新
        TbContentCategory parentNode = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            //更新父节点
            tbContentCategoryMapper.updateByPrimaryKey(parentNode);
        }
        //需要返回新插入内容类型数据的主键
        //返回TaotaoResult，其中封装TbContentCategory对象
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult modifyContentCategory(long parentId, String name) {
        //查询当前内容类型信息
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        //修改内容类型名称
        tbContentCategory.setName(name);
        //更新内容信息
        tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        //返回结果对象
        return TaotaoResult.ok(tbContentCategory);

    }

    @Override
    public TaotaoResult deleteContentCategory(long id) {
        //查询获取当前节点
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        //获取当前节点的父节点
        long parentId= tbContentCategory.getParentId();
        //删除当前节点
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        //查询与当前节点同一父节点的子节点个数，如果个数小于1则修改父节点的isParent属性
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        int sonNodeCount = tbContentCategoryMapper.countByExample(example);
        if(sonNodeCount<1){
            TbContentCategory parent=tbContentCategoryMapper.selectByPrimaryKey(parentId);
            parent.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }
        //查询当前节点是否有子节点,进行递归删除
        try {
            recursiveDeleteSonNode(tbContentCategory);
        } catch (Exception e) {
            throw new RuntimeException("递归删除内容类型子节点失败："+e.getMessage());
        }
        return TaotaoResult.ok();
    }

    /**
     * 递归删除子节点
     * @param tbContentCategory 父节点对象
     */
    private void recursiveDeleteSonNode(TbContentCategory tbContentCategory) {
        if(tbContentCategory.getIsParent()){
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria=example.createCriteria();
            criteria.andParentIdEqualTo(tbContentCategory.getId());
            List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
            for(TbContentCategory category:tbContentCategories){
                tbContentCategoryMapper.deleteByPrimaryKey(category.getId());
                recursiveDeleteSonNode(category);
            }
        }
    }
}
