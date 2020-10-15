package com.taotao.controller;

import com.taotao.common.pojo.EasyUiTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xxbb
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUiTreeNode> getContentCategoryList(
            @RequestParam(value="id",defaultValue = "0") Long parentId){
        return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId,String name){
        return contentCategoryService.addContentCategory(parentId,name);
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,String name){
        return contentCategoryService.modifyContentCategory(id,name);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        return contentCategoryService.deleteContentCategory(id);
    }
}
