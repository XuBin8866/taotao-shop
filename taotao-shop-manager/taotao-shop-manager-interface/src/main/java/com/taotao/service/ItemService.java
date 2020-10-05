package com.taotao.service;


import com.taotao.common.pojo.EasyUiDataGridResult;
import com.taotao.pojo.TbItem;

/**
 * @author xxbb
 */
public interface ItemService {
    /**
     * 根据物品Id查询商品
     * @param itemId id
     * @return 商品
     */
    TbItem getItemById(long itemId);

    /**
     * 查询商品列表
     * @param page 页码
     * @param rows 单页记录数
     * @return 记录对象
     */
    EasyUiDataGridResult getItemList(int page,int rows);
}
