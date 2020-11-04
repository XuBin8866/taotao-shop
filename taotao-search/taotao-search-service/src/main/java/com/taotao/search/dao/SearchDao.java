package com.taotao.search.dao;

/**
 * @author xxbb
 */

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws SolrServerException {
        SolrParams params;
        QueryResponse response=solrServer.query(query);
        SolrDocumentList solrDocuments=response.getResults();
        List<SearchItem> items=new ArrayList<>();
        for(SolrDocument s:solrDocuments){
            SearchItem item=new SearchItem();
            item.setId((String) s.get("id"));
            item.setCategory_name((String) s.get("item_category_name"));
            item.setPrice((Long) s.get("item_price"));
            item.setId((String) s.get("item_image"));
            item.setSell_point((String) s.get("item_sell_point"));
            //高亮显示
            Map<String,Map<String,List<String>>> highlighting=response.getHighlighting();
            List<String> list=highlighting.get(s.get("id")).get("item_title");
            String itemTitle="";
            //有高亮显示内容时
            if(list!=null&&list.size()!=0){
                itemTitle=list.get(0);
            }else{
                itemTitle= (String) s.get("item_title");
            }
            item.setTitle(itemTitle);
            items.add(item);
        }
        SearchResult result=new SearchResult();
        result.setItemList(items);
        result.setRecordCount(solrDocuments.getNumFound());

        return result;

    }
}
