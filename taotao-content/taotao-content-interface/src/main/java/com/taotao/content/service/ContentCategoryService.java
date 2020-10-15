package com.taotao.content.service;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * 内容类型service
 * @author xxbb
 */
public interface ContentCategoryService {
    /**
     * 查询内容类型
     * @param parentId 父id
     * @return 内容树
     */
    List<EasyUiTreeNode> getContentCategoryList(long parentId);

    /**
     * 添加内容类型
     * @param parentId 父id
     * @param name 内容名称
     * @return 结果集
     */
    TaotaoResult addContentCategory(long parentId,String name);

    /**
     * 修改内容类型名称
     * @param id 自身节点id
     * @param name 内容名称
     * @return 结果集
     */
    TaotaoResult modifyContentCategory(long id,String name);

    /**
     * 删除内容类型节点
     * @param id 节点id
     * @return 结果集
     */
    TaotaoResult deleteContentCategory(long id);
}
