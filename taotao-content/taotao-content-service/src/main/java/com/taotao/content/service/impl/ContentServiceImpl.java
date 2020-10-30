package com.taotao.content.service.impl;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtil;
import com.taotao.content.service.ContentService;
import com.taotao.content.service.jedis.JedisClientCluster;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import sun.swing.CachedPainter;

import java.util.Date;
import java.util.List;

/**
 * @author xxbb
 */
@Service
@PropertySource("classpath:properties/resource.properties")
public class ContentServiceImpl implements ContentService {


    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Value("${INDEX_CONTENT}")
    private String contentKey;

    @Override
    public TaotaoResult addContent(TbContent tbContent) {
        //补全content属性
        Date modifyDate = new Date();
        tbContent.setCreated(modifyDate);
        tbContent.setUpdated(modifyDate);
        //插入数据
        tbContentMapper.insert(tbContent);
        //删除缓存
        jedisClientCluster.hdel(contentKey,tbContent.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentList(long cid) {
        //查询缓存
        try{
            String jedisResult=jedisClientCluster.hget(contentKey,cid+"");
            if(StringUtils.isNotBlank(jedisResult)){
                return JsonUtil.jsonToList(jedisResult,TbContent.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> result = tbContentMapper.selectByExample(example);
        try{
            jedisClientCluster.hset(contentKey,cid+"",JsonUtil.objectToJson(result));
        }catch (Exception e){
             e.printStackTrace();
        }
        return result;

    }
}
