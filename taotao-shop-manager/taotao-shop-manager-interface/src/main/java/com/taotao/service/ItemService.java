package com.taotao.service;


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
}
