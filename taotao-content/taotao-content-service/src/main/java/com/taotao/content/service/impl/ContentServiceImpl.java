package com.taotao.content.service.impl;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xxbb
 */
@Service
public class ContentServiceImpl implements ContentService {


    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        //补全content属性
        Date modifyDate=new Date();
        tbContent.setCreated(modifyDate);
        tbContent.setUpdated(modifyDate);
        //插入数据
        tbContentMapper.insert(tbContent);
        return TaotaoResult.ok();
    }
}
