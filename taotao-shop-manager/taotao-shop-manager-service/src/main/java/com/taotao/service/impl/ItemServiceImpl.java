package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理服务
 * @author xxbb
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;


    @Override
    public TbItem getItemById(long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUiDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = itemMapper.selectByExample(new TbItemExample());
        PageInfo<TbItem> pageInfo=new PageInfo<>(tbItems);
        EasyUiDataGridResult result=new EasyUiDataGridResult();
        result.setRows(tbItems);
        result.setTotal(pageInfo.getTotal());
        return result;
    }
}
