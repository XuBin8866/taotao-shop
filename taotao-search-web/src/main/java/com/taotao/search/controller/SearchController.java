package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * @author xxbb
 */
@Controller
public class SearchController {

    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page, Model model) throws SolrServerException, UnsupportedEncodingException {
        queryString=new String(queryString.getBytes("ISO8859-1"),"UTF-8");
        SearchResult result=searchService.search(queryString,page,ITEM_ROWS);
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",result.getTotalPages());
        model.addAttribute("itemList",result.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
    @RequestMapping("/index")
    public String toIndex(){
        return "search";
    }
}
