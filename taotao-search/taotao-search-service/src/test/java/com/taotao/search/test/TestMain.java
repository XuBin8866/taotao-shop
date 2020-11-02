package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author xxbb
 */
public class TestMain {
    private static final String baseUrl="http://192.168.17.2:8080/solr/collection1";
//    @Test
//    public void testAddDocument() throws IOException, SolrServerException {
//        //创建一个SolrServer的实现类HttpSolrServer对象
//        //指定solr服务URL
//        SolrServer solrServer=new HttpSolrServer(
//                baseUrl);
//        //创建一个文档对象SolrInputDocument
//        SolrInputDocument document=new SolrInputDocument();
//        //向文档中添加域，必须有ID域
//        document.addField("id","test002");
//        document.addField("item_title","测试商品2");
//        document.addField("item_price","1000");
//        //把文档对象写入索引库
//        solrServer.add(document);
//        //提交
//        solrServer.commit();
//    }
//    @Test
//    public void testDeleteDocument() throws IOException, SolrServerException {
//        SolrServer solrServer=new HttpSolrServer(
//                baseUrl);
//        UpdateResponse test002 = solrServer.deleteByQuery("*:*");
//        solrServer.commit();
//    }
//    @Test
//    public void queryDocument() throws SolrServerException {
//        SolrServer solrServer=new HttpSolrServer(baseUrl);
//        SolrQuery solrQuery=new SolrQuery();
//        solrQuery.setQuery("*:*");
//        QueryResponse query = solrServer.query(solrQuery);
//        SolrDocumentList solrDocuments=query.getResults();
//        System.out.println(solrDocuments.getNumFound());
//        for(SolrDocument solrDocument:solrDocuments){
//            System.out.println(solrDocument.get("id"));
//            System.out.println(solrDocument.get("item_title"));
//            System.out.println(solrDocument.get("item_price"));
//        }
//    }
//    @Test
//    public void queryDocumentWithHighLighting() throws SolrServerException {
//        SolrServer solrServer=new HttpSolrServer(baseUrl);
//        SolrQuery query=new SolrQuery();
//        query.setQuery("测试");
//        //指定默认搜素域
//        query.set("df","item_keywords");
//        //开启高亮显示
//        query.setHighlight(true);
//        //高亮显示域
//        query.addHighlightField("item_title");
//        query.setHighlightSimplePre("<em>");
//        //执行查询
//        QueryResponse response=solrServer.query(query);
//        SolrDocumentList solrDocuments=response.getResults();
//        System.out.println(solrDocuments.getNumFound());
//        for(SolrDocument solrDocument:solrDocuments){
//            System.out.println(solrDocument.get("id"));
//            //高亮显示
//            Map<String,Map<String, List<String>>> highlighting=response.getHighlighting();
//            Object id = solrDocument.get("id");
//            List<String> list=highlighting.get(id).get("item_title");
//            String itemTitle=null;
//            if(list!=null&&list.size()>0){
//                itemTitle=list.get(0);
//            }else{
//                itemTitle=(String)solrDocument.get("item_title");
//            }
//            System.out.println(itemTitle);
//            System.out.println(solrDocument.get("item_price"));
//        }
//    }
}
