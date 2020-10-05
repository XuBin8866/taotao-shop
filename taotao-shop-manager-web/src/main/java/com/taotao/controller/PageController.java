package com.taotao.controller;

import com.taotao.common.pojo.EasyUiDataGridResult;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xxbb
 */
@Controller
public class PageController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUiDataGridResult getItemList(Integer page,Integer rows){
        EasyUiDataGridResult result= itemService.getItemList(page,rows);
        return result;
    }


}
