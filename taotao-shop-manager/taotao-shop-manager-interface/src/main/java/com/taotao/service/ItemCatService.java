package com.taotao.service;




import com.taotao.common.pojo.EasyUiTreeNode;

import java.util.List;

/**
 * 商品类目查询
 * @author xxbb
 */
public interface ItemCatService {
    /**
     * 查询商品类目
     * @param parentId 商品类目父编号
     * @return 商品类目
     */
    List<EasyUiTreeNode> getCatList(long parentId);
}
