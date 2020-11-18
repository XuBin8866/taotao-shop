package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtil;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * 商品管理服务
 * @author xxbb
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource(name="itemAddtopic")
    private Destination topicDestination;

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

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        //生成商品ID
        long itemId= IDUtil.genItemId();
        //补全item属性
        item.setId(itemId);
            //商品状态：1.正常 2.下架 3.删除
        item.setStatus((byte) 1);
            //设置修改时间
        Date date=new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //向商品表中插入数据
        itemMapper.insert(item);
        //创建一个商品描述类TbItemDesc
        TbItemDesc itemDesc=new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //补全pojo属性
        // 向商品描述表中插入数据
        itemDescMapper.insert(itemDesc);
        //发送一个商品添加消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                System.out.println("create message to item-add-topic");
                return session.createTextMessage(itemId+"");
            }
        });
        //返回结果
        return TaotaoResult.ok();
    }
}
