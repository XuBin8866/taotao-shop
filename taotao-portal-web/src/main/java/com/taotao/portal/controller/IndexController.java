package com.taotao.portal.controller;

import com.taotao.common.util.JsonUtil;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxbb
 */
@Controller
public class IndexController {

    @Value("${AD1_CID}")
    private Long AD1_CID;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public ModelAndView showIndex(){
        List<TbContent> contentList = contentService.getContentList(AD1_CID);
        ModelAndView modelAndView=new ModelAndView();
        List<Ad1Node> ad1NodeList=new ArrayList<>();
        for(TbContent tbContent:contentList){
            Ad1Node node=new Ad1Node();
            node.setAlt(tbContent.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());

            ad1NodeList.add(node);
        }
        String jsonAd=JsonUtil.objectToJson(ad1NodeList);
        modelAndView.addObject("ad1", jsonAd);
        modelAndView.setViewName("index");

        return modelAndView;
    }


}
