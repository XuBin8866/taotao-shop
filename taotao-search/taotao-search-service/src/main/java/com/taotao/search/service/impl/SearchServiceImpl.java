package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxbb
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws SolrServerException {
        SolrQuery query=new SolrQuery();
        query.setQuery(queryString);
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //指定默认搜索域
        query.set("df","item_title");
        //设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");

        SearchResult result=searchDao.search(query);
        long recordCount=result.getRecordCount();
        long pageCount=recordCount/rows;
        if(recordCount%2!=0){
            pageCount++;
        }
        result.setTotalPages(pageCount);
        return result;
    }
}
