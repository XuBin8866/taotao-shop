package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author xxbb
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemsToIndex() {
        try {
            List<SearchItem> itemList = searchItemMapper.getItemList();
            for (SearchItem searchItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                document.addField("item_desc", searchItem.getItem_desc());
                //把文档写入索引库
                try {
                    solrServer.add(document);
                } catch (SolrServerException | IOException e) {
                    throw new RuntimeException("索引库添加数据失败" + e.getMessage());
                }
            }
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "数据导入失败");
        }
        return TaotaoResult.ok();

    }
}

