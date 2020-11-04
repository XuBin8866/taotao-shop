package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * @author xxbb
 */
public interface SearchService {
    SearchResult search(String queryString,int page,int rows) throws SolrServerException;
}
