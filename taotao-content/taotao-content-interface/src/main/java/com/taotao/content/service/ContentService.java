package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * @author xxbb
 */
public interface ContentService {
    /**
     * 添加内容
     * @param tbContent 内容对象
     * @return 结果集
     */
    TaotaoResult addContent(TbContent tbContent);

    /**
     * 获取商城首页轮播图
     * @param cid
     * @return
     */
    List<TbContent> getContentList(long cid);
}
