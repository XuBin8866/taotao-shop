package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xxbb
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importAllItems(){
        return searchItemService.importItemsToIndex();
    }
}
