package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xxbb
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello(){
        System.out.println("我套你猴子！！！");
        return "success";
    }
}
